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
public class ShipmentPk implements Serializable {

	@Column(name = "SHIPMENT_ID", length = 100)
	private String shipmentId;

	@Column(name = "APPLICANT_KEY", length = 100)
	private String applicantKey;

	public ShipmentPk(String shipmentId, String applicantKey) {
		this.shipmentId = shipmentId;
		this.applicantKey = applicantKey;
	}
}

