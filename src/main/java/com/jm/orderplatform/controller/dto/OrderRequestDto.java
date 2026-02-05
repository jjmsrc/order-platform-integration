package com.jm.orderplatform.controller.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@JacksonXmlRootElement(localName = "REQUEST")
@Data
@Builder
public class OrderRequestDto {

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "HEADER")
	@NotEmpty(message = "HEADER list must not be empty")
	@Valid
	private List<HeaderDto> headers;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "ITEM")
	@NotEmpty(message = "ITEM list must not be empty")
	@Valid
	private List<ItemDto> items;

}