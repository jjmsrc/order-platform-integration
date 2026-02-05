package com.jm.orderplatform.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jm.orderplatform.global.exception.CustomException;
import com.jm.orderplatform.global.exception.ErrorCode;
import com.jm.orderplatform.global.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(CustomException e) {

		if (e.getCause() != null) {
			log.error("System error occurred", e); // cause 포함
		} else {
			log.info("Business error occurred: {}", e.getMessage());
		}

		return ResponseEntity
			.status(e.getErrorCode().status())
			.body(ErrorResponse.from(e));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAll(Exception e) {

		log.error("예상치 못한 예외가 발생하였습니다.", e);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ErrorResponse.from(
				new CustomException(ErrorCode.INTERNAL_ERROR, "알 수 없는 오류가 발생했습니다.")
			));
	}

}
