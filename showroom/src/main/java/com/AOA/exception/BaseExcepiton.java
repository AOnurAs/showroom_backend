package com.AOA.exception;

public class BaseExcepiton extends RuntimeException{
	
	public BaseExcepiton(ErrorMessage errorMessage) {
		super(errorMessage.prepareErrorMessage());
	}

}
