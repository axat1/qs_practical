package com.coding.qs_practical.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.coding.qs_practical.entity.User;
import com.coding.qs_practical.exception.UsernameNotFoundException;
import com.coding.qs_practical.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService { 
  
    @Autowired
    private UserRepository repository; 
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
  
        Optional<User> userDetail = repository.findByUsername(username); 
  
        // Converting userDetail to UserDetails 
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
    
} 