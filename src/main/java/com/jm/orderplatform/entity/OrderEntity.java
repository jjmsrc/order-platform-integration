package com.jm.orderplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_TB")
@Getter
@NoArgsConstructor
public class OrderEntity {

	@EmbeddedId
	private OrderPk id;

	@Column(name = "USER_ID", length = 100)
	private String userId;

	@Column(name = "ITEM_ID", length = 100)
	private String itemId;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "ADDRESS", length = 100)
	private String address;

	@Column(name = "ITEM_NAME", length = 100)
	private String itemName;

	@Column(name = "PRICE", length = 100)
	private String price;

	@Column(name = "STATUS", length = 100)
	private String status;

	public OrderEntity(OrderPk id, String userId, String itemId,
		String name, String address, String itemName,
		String price, String status) {
		this.id = id;
		this.userId = userId;
		this.itemId = itemId;
		this.name = name;
		this.address = address;
		this.itemName = itemName;
		this.price = price;
		this.status = status;
	}
}
