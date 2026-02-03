package com.jm.orderplatform.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemDto {

	@JacksonXmlProperty(localName = "USER_ID")
	@NotBlank(message = "USER_ID is required")
	@Size(max = 100, message = "USER_ID must be <= 100 characters")
	@Pattern(
		regexp = "^USER\\d+$",
		message = "USER_ID format is invalid"
	)
	private String userId;

	@JacksonXmlProperty(localName = "ITEM_ID")
	@NotBlank(message = "ITEM_ID is required")
	@Size(max = 100, message = "ITEM_ID must be <= 100 characters")
	@Pattern(
		regexp = "^ITEM\\d+$",
		message = "ITEM_ID format is invalid"
	)
	private String itemId;

	@JacksonXmlProperty(localName = "ITEM_NAME")
	@NotBlank(message = "ITEM_NAME is required")
	@Size(max = 100, message = "ITEM_NAME must be <= 100 characters")
	private String itemName;

	@JacksonXmlProperty(localName = "PRICE")
	@NotBlank(message = "PRICE is required")
	@Size(max = 100, message = "PRICE must be <= 100 characters")
	@Pattern(
		regexp = "^\\d+$",
		message = "PRICE must be numeric"
	)
	private String price;

}
