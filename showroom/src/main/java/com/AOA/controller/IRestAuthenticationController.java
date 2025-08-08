package com.AOA.controller;

import com.AOA.dto.AuthRequest;
import com.AOA.dto.AuthResponse;
import com.AOA.dto.DtoUser;
import com.AOA.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {
	
	public RootEntity<DtoUser> register(AuthRequest request);
	
	public RootEntity<AuthResponse> authenticate(AuthRequest request);
	
	public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}
