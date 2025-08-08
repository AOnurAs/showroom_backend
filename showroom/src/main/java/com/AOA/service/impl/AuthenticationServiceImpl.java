package com.AOA.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.AOA.dto.AuthRequest;
import com.AOA.dto.AuthResponse;
import com.AOA.dto.DtoUser;
import com.AOA.dto.RefreshTokenRequest;
import com.AOA.exception.BaseExcepiton;
import com.AOA.exception.ErrorMessage;
import com.AOA.exception.MessageType;
import com.AOA.jwt.JwtService;
import com.AOA.model.RefreshToken;
import com.AOA.model.User;
import com.AOA.repository.RefreshTokenRepository;
import com.AOA.repository.UserRepository;
import com.AOA.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	private User createUser(AuthRequest input){
		User user = new User();
		user.setCreateTime(new Date());
		user.setUsername(input.getUsername());
		String encodedPassword = passwordEncoder.encode(input.getPassword());
		user.setPassword(encodedPassword);
		
		return user;
	}
	
	@Override
	public DtoUser register(AuthRequest input) {
		DtoUser dtoUser = new DtoUser();
		
		User user = createUser(input);
		userRepository.save(user);
		
		BeanUtils.copyProperties(user, dtoUser);
		 
		return dtoUser;
	}
	
	private RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setCreateTime(new Date());
		refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setUser(user);
		
		return refreshToken;
		
	}

	@Override
	public AuthResponse authenticate(AuthRequest req) {
		
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
			authenticationProvider.authenticate(authToken);
			
			Optional<User> optionalUser = userRepository.findByUsername(req.getUsername());
			String accessToken = jwtService.generateToken(optionalUser.get());
			
			RefreshToken refreshToken = createRefreshToken(optionalUser.get());
			refreshTokenRepository.save(refreshToken);
			
			return new AuthResponse(accessToken, refreshToken.getRefreshToken());
			
		} catch (Exception e) {
			throw new BaseExcepiton(new ErrorMessage(MessageType.INVALID_USERNAME_PASSWORD_COMBINATION, e.getMessage()));
		}
	}
	
	public boolean isRefreshTokenExpired(Date expiryDate){
		
		return new Date().after(expiryDate);
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest req) {
		AuthResponse authResponse = new AuthResponse();
		
		Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(req.getRefreshToken());
		if(optRefreshToken.isEmpty()) {
			throw new BaseExcepiton(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, "Refresh token couldnt be found in db => " + req.getRefreshToken()));
		}
		
		if(isRefreshTokenExpired(optRefreshToken.get().getExpireDate())) {
			throw new BaseExcepiton(new ErrorMessage(MessageType.REFRESH_TOKEN_EXPIRED, "Refresh token expired => " + req.getRefreshToken()));
		}
		
		String accessToken = jwtService.generateToken(optRefreshToken.get().getUser());
		
		RefreshToken refreshToken = createRefreshToken(optRefreshToken.get().getUser());
		refreshTokenRepository.save(refreshToken);
		String refreshTokenString = refreshToken.getRefreshToken();
		
		authResponse.setRefreshToken(refreshTokenString);
		authResponse.setAccessToken(accessToken);
		
		return authResponse;
	}

}
