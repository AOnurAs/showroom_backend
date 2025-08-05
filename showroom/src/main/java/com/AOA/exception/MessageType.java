package com.AOA.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	NO_RECORD_EXIST("1004", "No record found"),
	GENERAL_EXCEPTION("9999", "An error occured");
	
	private String code;
	private String message;
	
	MessageType(String code, String message) {
		this.message = message;
		this.code = code;
	}
}
