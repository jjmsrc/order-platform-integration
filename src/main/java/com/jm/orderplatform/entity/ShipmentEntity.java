package com.jm.orderplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHIPMENT_TB")
@Getter
@NoArgsConstructor
public class ShipmentEntity {

	@EmbeddedId
	private ShipmentPk id;

	@Column(name = "ORDER_ID", length = 100)
	private String orderId;

	@Column(name = "ITEM_ID", length = 100)
	private String itemId;

	@Column(name = "ADDRESS", length = 100)
	private String address;

	public ShipmentEntity(ShipmentPk id, String orderId, String itemId, String address) {
		this.id = id;
		this.orderId = orderId;
		this.itemId = itemId;
		this.address = address;
	}
}
