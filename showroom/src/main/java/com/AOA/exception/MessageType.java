package com.AOA.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	NO_RECORD_EXIST("1004", "No record found"),
	TOKEN_NOT_FOUND("1005", "Token coudnt be found in the request"),
	GENERAL_EXCEPTION("9999", "An error occured"),
	TOKEN_EXPIRED("1008", "Token expired");
	
	private String code;
	private String message;
	
	MessageType(String code, String message) {
		this.message = message;
		this.code = code;
	}
}
