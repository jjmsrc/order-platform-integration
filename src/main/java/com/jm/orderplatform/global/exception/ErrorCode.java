package com.jm.orderplatform.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// 400
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	INVALID_XML_ERROR(HttpStatus.BAD_REQUEST, "XML 형식이 잘못되었습니다."),
	INVALID_ITEM_ERROR(HttpStatus.BAD_REQUEST, "주문을 요청한 사용자 정보를 찾을 수 없습니다."),
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "입력 형식이 잘못되었습니다."),
	ORDER_MAX_COUNT_ERROR(HttpStatus.BAD_REQUEST, "주문 내역의 개수가 초과하였습니다."),

	// 404
	EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 요청입니다."),

	// 500
	INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
	EXTERNAL_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 시스템 연계 중 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus status() {
		return status;
	}

	public String message() {
		return message;
	}
}

