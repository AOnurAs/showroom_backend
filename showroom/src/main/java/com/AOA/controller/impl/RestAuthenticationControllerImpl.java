package com.AOA.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AOA.controller.IRestAuthenticationController;
import com.AOA.controller.RestBaseController;
import com.AOA.controller.RootEntity;
import com.AOA.dto.AuthRequest;
import com.AOA.dto.AuthResponse;
import com.AOA.dto.DtoUser;
import com.AOA.dto.RefreshTokenRequest;
import com.AOA.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {
	
	@Autowired
	private IAuthenticationService authenticationService;

	@PostMapping("/register")
	@Override
	public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest request) {
		return ok(authenticationService.register(request));
	}

	@PostMapping("/authenticate")
	@Override
	public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
		return ok(authenticationService.authenticate(request));
	}

	@PostMapping("/refreshToken")
	@Override
	public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return ok(authenticationService.refreshToken(request));
	}

}
