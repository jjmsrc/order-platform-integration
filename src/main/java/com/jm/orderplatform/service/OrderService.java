package com.jm.orderplatform.service;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.jm.orderplatform.controller.dto.HeaderDto;
import com.jm.orderplatform.controller.dto.ItemDto;
import com.jm.orderplatform.controller.dto.OrderRequestDto;
import com.jm.orderplatform.entity.OrderEntity;
import com.jm.orderplatform.entity.OrderPk;
import com.jm.orderplatform.global.exception.CustomException;
import com.jm.orderplatform.global.exception.ErrorCode;
import com.jm.orderplatform.repository.OrderRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final Validator validator;
	private final SftpRemoteFileTemplate sftpTemplate;

	@Value("${applicant.key}")
	private String APPLICANT_KEY;

	@Transactional
	public void sendOrder(String requestBody) {

		log.info("주문 적재 요청 시작");

		// 요청 메시지 파싱 및 검증
		OrderRequestDto orderRequestDto = parseOrderXml(requestBody);
		validateOrderRequest(orderRequestDto);

		// 엔티티 데이터 변환
		List<OrderEntity> orderEntityList = makeOrderEntityList(orderRequestDto);

		// 주문 정보 DB 저장
		orderRepository.saveAll(orderEntityList);

		// 주문 정보 회계 파일 전송
		sendSftp(orderEntityList);

		log.info("주문 적재 요청 성공 - 개수: {}", orderEntityList.size());

	}

	public static OrderRequestDto parseOrderXml(String xml) {

		XMLInputFactory factory = XMLInputFactory.newInstance();

		List<HeaderDto> headers = new ArrayList<>();
		List<ItemDto> items = new ArrayList<>();

		try {
			XMLStreamReader reader =
				factory.createXMLStreamReader(new StringReader(xml));

			HeaderDto currentHeader = null;
			ItemDto currentItem = null;
			String currentTag = null;

			while (reader.hasNext()) {
				int event = reader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					currentTag = reader.getLocalName();

					if ("HEADER".equals(currentTag)) {
						currentHeader = new HeaderDto();
					}
					if ("ITEM".equals(currentTag)) {
						currentItem = new ItemDto();
					}
				}

				if (event == XMLStreamConstants.CHARACTERS) {
					String value = reader.getText().trim();
					if (value.isEmpty())
						continue;

					if (currentTag == null)
						throw new CustomException(ErrorCode.INVALID_XML_ERROR);

					if (currentHeader != null) {
						switch (currentTag) {
							case "USER_ID" -> currentHeader.setUserId(value);
							case "NAME" -> currentHeader.setName(value);
							case "ADDRESS" -> currentHeader.setAddress(value);
							case "STATUS" -> currentHeader.setStatus(value);
						}
					}

					if (currentItem != null) {
						switch (currentTag) {
							case "USER_ID" -> currentItem.setUserId(value);
							case "ITEM_ID" -> currentItem.setItemId(value);
							case "ITEM_NAME" -> currentItem.setItemName(value);
							case "PRICE" -> currentItem.setPrice(value);
						}
					}
				}

				if (event == XMLStreamConstants.END_ELEMENT) {
					if ("HEADER".equals(reader.getLocalName())) {
						headers.add(currentHeader);
						currentHeader = null;
					}
					if ("ITEM".equals(reader.getLocalName())) {
						items.add(currentItem);
						currentItem = null;
					}
					currentTag = null;
				}
			}

		} catch (Exception e) {
			throw new CustomException(ErrorCode.INVALID_XML_ERROR);
		}

		return OrderRequestDto.builder()
			.headers(headers)
			.items(items)
			.build();
	}

	private void validateOrderRequest(OrderRequestDto orderRequestDto) {
		Set<ConstraintViolation<OrderRequestDto>> violations =
			validator.validate(orderRequestDto);

		if (!violations.isEmpty()) {
			throw new CustomException(
				ErrorCode.VALIDATION_ERROR,
				violations.iterator().next().getMessage());
		}

	}

	private String nextOrderId(String id) {
		if (id.endsWith("999")) {
			if (id.charAt(0) == 'Z') {
				throw new CustomException(ErrorCode.ORDER_MAX_COUNT_ERROR);
			}
			return (id.charAt(0) + 1) + "000";
		}
		return id.charAt(0) + String.format("%03d", Integer.parseInt(id.substring(1)) + 1);
	}

	private List<OrderEntity> makeOrderEntityList(OrderRequestDto orderRequestDto) {
		List<OrderEntity> orderEntityList = new ArrayList<>();

		// 가장 최근의 ORDER_ID 조회 후 다음 ID 생성
		String orderId = "A001";
		List<OrderEntity> latestOrder = orderRepository.findLatestOrder(PageRequest.of(0, 1));
		if (!latestOrder.isEmpty()) {
			orderId = nextOrderId(latestOrder.get(0).getId().getOrderId());
		}

		// 헤더(사용자) 정보 맵 생성
		Map<String, HeaderDto> headerMap = orderRequestDto.getHeaders().stream()
			.collect(Collectors.toMap(HeaderDto::getUserId, h -> h));

		// 아이템 별 주문 정보 생성
		for (ItemDto item : orderRequestDto.getItems()) {
			HeaderDto header = headerMap.get(item.getUserId());
			if (Objects.isNull(header)) {
				log.debug("존재하지 않는 사용자: {}", item.getUserId());
				throw new CustomException(ErrorCode.INVALID_ITEM_ERROR);
			}
			OrderPk orderPk = OrderPk.builder()
				.orderId(orderId)
				.applicantKey(APPLICANT_KEY)
				.build();
			orderId = nextOrderId(orderId);
			OrderEntity orderEntity = OrderEntity.builder()
				.id(orderPk)
				.userId(header.getUserId())
				.itemId(item.getItemId())
				.name(header.getName())
				.address(header.getAddress())
				.itemName(item.getItemName())
				.price(item.getPrice())
				.status(header.getStatus())
				.build();
			orderEntityList.add(orderEntity);
		}

		return orderEntityList;
	}

	private void sendSftp(List<OrderEntity> orderEntityList) {
		StringBuilder sb = new StringBuilder();
		for (OrderEntity e : orderEntityList) {
			sb
				.append(e.getId().getOrderId()).append('^')
				.append(e.getUserId()).append('^')
				.append(e.getItemId()).append('^')
				.append(e.getId().getApplicantKey()).append('^')
				.append(e.getName()).append('^')
				.append(e.getAddress()).append('^')
				.append(e.getItemName()).append('^')
				.append(e.getPrice())
				.append('\n')
			;
		}
		sendSftp(sb.toString());
	}

	private void sendSftp(String data) {
		String timestamp = LocalDateTime.now()
			.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String remoteFileName = "INSPIEN_김지민_" + timestamp + ".txt";

		try {

			byte[] content = data.getBytes(StandardCharsets.UTF_8);

			Message<byte[]> message = MessageBuilder
				.withPayload(content)
				.setHeader(FileHeaders.FILENAME, remoteFileName)
				.build();

			sftpTemplate.send(message, FileExistsMode.REPLACE);

		} catch (Exception e) {
			log.error("SFTP send failed. filename = {}", remoteFileName);
			throw new CustomException(ErrorCode.EXTERNAL_SYSTEM_ERROR, e);
		}

	}

}
