package com.jm.orderplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHIPMENT_TB")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentEntity {

	@EmbeddedId
	private ShipmentPk id;

	@Column(name = "ORDER_ID", length = 100)
	private String orderId;

	@Column(name = "ITEM_ID", length = 100)
	private String itemId;

	@Column(name = "ADDRESS", length = 100)
	private String address;

}
