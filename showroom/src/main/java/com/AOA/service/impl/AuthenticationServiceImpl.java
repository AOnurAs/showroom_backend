package com.AOA.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.AOA.dto.AuthRequest;
import com.AOA.dto.DtoUser;
import com.AOA.model.User;
import com.AOA.repository.UserRepository;
import com.AOA.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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

}
