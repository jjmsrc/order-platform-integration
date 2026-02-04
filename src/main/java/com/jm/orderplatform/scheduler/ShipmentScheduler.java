package com.jm.orderplatform.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jm.orderplatform.service.ShipmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipmentScheduler {

	private final ShipmentService shipmentService;

	@Scheduled(cron = "0 */5 * * * *")
	public void processOrders() {
		shipmentService.updateShipmentStatus();
	}

}
