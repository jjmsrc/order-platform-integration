package com.jm.orderplatform.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String detailMessage;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.message());
		this.errorCode = errorCode;
		this.detailMessage = null;
	}

	public CustomException(ErrorCode errorCode, String detailMessage) {
		super(detailMessage);
		this.errorCode = errorCode;
		this.detailMessage = detailMessage;
	}

	public CustomException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.message(), cause);
		this.errorCode = errorCode;
		this.detailMessage = null;
	}

}
