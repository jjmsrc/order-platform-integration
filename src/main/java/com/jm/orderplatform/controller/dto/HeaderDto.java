package com.jm.orderplatform.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HeaderDto {

	@JacksonXmlProperty(localName = "USER_ID")
	@NotBlank(message = "USER_ID is required")
	@Size(max = 100, message = "USER_ID must be <= 100 characters")
	@Pattern(
		regexp = "^USER\\d+$",
		message = "USER_ID format is invalid (ex: USER12)"
	)
	private String userId;

	@JacksonXmlProperty(localName = "NAME")
	@NotBlank(message = "NAME is required")
	@Size(max = 100, message = "NAME must be <= 100 characters")
	private String name;

	@JacksonXmlProperty(localName = "ADDRESS")
	@NotBlank(message = "ADDRESS is required")
	@Size(max = 100, message = "ADDRESS must be <= 100 characters")
	private String address;

	@JacksonXmlProperty(localName = "STATUS")
	@NotBlank(message = "STATUS is required")
	@Size(max = 100, message = "STATUS must be <= 100 characters")
	@Pattern(
		regexp = "^[YN]$",
		message = "STATUS must be Y or N"
	)
	private String status;

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

}

