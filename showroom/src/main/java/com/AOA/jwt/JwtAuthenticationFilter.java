package com.AOA.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.AOA.exception.BaseExcepiton;
import com.AOA.exception.ErrorMessage;
import com.AOA.exception.MessageType;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		if(header == null) {
			filterChain.doFilter(request, response);
			throw new BaseExcepiton(new ErrorMessage(MessageType.TOKEN_NOT_FOUND, null));
			//return;
		}
		
		String token = header.substring(7);
		String username;
		try {
			username = jwtService.getUsernameByToken(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if(userDetails != null && jwtService.isTokenValid(token)) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
					authToken.setDetails(userDetails);
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}else {
				System.out.println("username is null for the request or getAuthentication is not null");
			}
		} catch (ExpiredJwtException e) {
			throw new BaseExcepiton(new ErrorMessage(MessageType.TOKEN_EXPIRED, e.getMessage()));
		} catch (Exception e) {
			throw new BaseExcepiton(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
		}
		
	}

}
