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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ShipmentPk implements Serializable {

	@Column(name = "SHIPMENT_ID", length = 100)
	private String shipmentId;

	@Column(name = "APPLICANT_KEY", length = 100)
	private String applicantKey;

}

