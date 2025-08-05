package com.AOA.service.impl;

import org.springframework.stereotype.Service;

import com.AOA.exception.BaseExcepiton;
import com.AOA.exception.ErrorMessage;
import com.AOA.exception.MessageType;
import com.AOA.service.IAddressService;

@Service
public class AddressServiceImpl implements IAddressService{
	
	public void ExcepitonTest() {
		//throw new BaseExcepiton(new ErrorMessage(MessageType.GENERAL_EXCEPTION, null));
	}

}
