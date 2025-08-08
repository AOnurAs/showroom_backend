package com.AOA.service;

import com.AOA.dto.AuthRequest;
import com.AOA.dto.AuthResponse;
import com.AOA.dto.DtoUser;
import com.AOA.dto.RefreshTokenRequest;

public interface IAuthenticationService {

	public DtoUser register(AuthRequest input);
	
	public AuthResponse authenticate(AuthRequest req);
	
	public AuthResponse refreshToken(RefreshTokenRequest req);
}
