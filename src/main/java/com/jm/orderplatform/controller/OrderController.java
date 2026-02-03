package com.jm.orderplatform.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jm.orderplatform.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@PostMapping(value = "/orders",
		consumes = MediaType.APPLICATION_XML_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Void> sendOrder(@RequestBody String requestBody) {
		log.info("주문 적재 요청 시작");
		orderService.sendOrder(requestBody);
		log.info("주문 적재 요청 성공");
		return ResponseEntity.ok().build();
	}

}
