package com.jm.orderplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderPk implements Serializable {

	@Column(name = "ORDER_ID", length = 100)
	private String orderId;

	@Column(name = "APPLICANT_KEY", length = 100)
	private String applicantKey;

	public OrderPk(String orderId, String applicantKey) {
		this.orderId = orderId;
		this.applicantKey = applicantKey;
	}
}
