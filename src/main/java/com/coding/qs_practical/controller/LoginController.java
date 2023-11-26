package com.coding.qs_practical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.qs_practical.config.auth.JwtService;
import com.coding.qs_practical.dto.AuthRequest;
import com.coding.qs_practical.exception.UsernameNotFoundException;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	@Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager;

	@PostMapping("/login") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getUsername(), authentication.getAuthorities()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 		
}
