package com.jm.orderplatform.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jm.orderplatform.global.exception.CustomException;
import com.jm.orderplatform.global.exception.ErrorCode;
import com.jm.orderplatform.global.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(CustomException e) {
		ErrorCode code = e.getErrorCode();
		return ResponseEntity
			.status(code.status())
			.body(ErrorResponse.from(code));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
		return ResponseEntity
			.badRequest()
			.body(new ErrorResponse(
				ErrorCode.VALIDATION_ERROR.name(),
				e.getBindingResult()
					.getFieldErrors()
					.get(0)
					.getDefaultMessage()
			));
	}

}
