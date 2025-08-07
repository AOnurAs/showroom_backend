package com.AOA.controller;

import com.AOA.dto.AuthRequest;
import com.AOA.dto.DtoUser;

public interface IRestAuthenticationController {
	
	public RootEntity<DtoUser> register(AuthRequest request);

}
