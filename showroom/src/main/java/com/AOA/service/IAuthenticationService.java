package com.AOA.service;

import com.AOA.dto.AuthRequest;
import com.AOA.dto.DtoUser;

public interface IAuthenticationService {

	public DtoUser register(AuthRequest input);
}
