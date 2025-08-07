package com.AOA.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	NO_RECORD_EXIST("1004", "No record found"),
	TOKEN_NOT_FOUND("1005", "Token coudnt be found in the request"),
	USERNAME_NOT_FOUND("1006", "Username not found in the database"),
	TOKEN_EXPIRED("1008", "Token expired"),
	GENERAL_EXCEPTION("9999", "An error occured");
	
	private String code;
	private String message;
	
	MessageType(String code, String message) {
		this.message = message;
		this.code = code;
	}
}
