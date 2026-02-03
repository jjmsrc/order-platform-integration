package com.jm.orderplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPk implements Serializable {

	@Column(name = "ORDER_ID", length = 100)
	private String orderId;

	@Column(name = "APPLICANT_KEY", length = 100)
	private String applicantKey;

}
