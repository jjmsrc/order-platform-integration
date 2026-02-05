package com.jm.orderplatform.global.exception;

public record ErrorResponse(
	String code,
	String message
) {
	public static ErrorResponse from(CustomException e) {
		return new ErrorResponse(
			e.getErrorCode().name(),
			e.getDetailMessage() != null
				? e.getDetailMessage()
				: e.getErrorCode().message()
		);
	}
}
