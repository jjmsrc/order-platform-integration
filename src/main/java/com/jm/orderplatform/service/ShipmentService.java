package com.jm.orderplatform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jm.orderplatform.entity.OrderEntity;
import com.jm.orderplatform.entity.ShipmentEntity;
import com.jm.orderplatform.entity.ShipmentPk;
import com.jm.orderplatform.repository.OrderRepository;
import com.jm.orderplatform.repository.ShipmentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentService {

	private final OrderRepository orderRepository;
	private final ShipmentRepository shipmentRepository;

	@Value("${applicant.key}")
	private String APPLICANT_KEY;

	@Transactional
	public void updateShipmentStatus() {

		log.info("운송 회사 DB 적재 요청 시작");

		// 미전송 주문 목록 조회

		List<OrderEntity> orderEntityList = orderRepository.findAllById_ApplicantKeyAndStatus(APPLICANT_KEY, "N");

		// 운송 DB에 데이터 저장

		List<ShipmentEntity> shipmentEntityList = orderEntityList.stream()
			.map(e -> ShipmentEntity.builder()
				.id(ShipmentPk.builder()
					.shipmentId(e.getId().getOrderId())
					.applicantKey(APPLICANT_KEY)
					.build())
				.orderId(e.getId().getOrderId())
				.itemId(e.getItemId())
				.address(e.getAddress())
				.build())
			.toList();

		shipmentRepository.saveAll(shipmentEntityList);

		// 주문 상태 업데이트

		for (OrderEntity orderEntity : orderEntityList) {
			orderEntity.updateStatus("Y");
		}

		log.info("운송 회사 DB 적재 요청 성공 - 건수: {}", orderEntityList.size());

	}

}
