package com.coding.qs_practical.config.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coding.qs_practical.service.UserInfoService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter { 
  
    private JwtService jwtService; 
    private UserInfoService userService; 
    
    public JwtAuthFilter(JwtService jwtService,
    					UserInfoService userService) {
    	this.jwtService = jwtService;
    	this.userService = userService;
    }
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String username = null; 
        Collection<? extends GrantedAuthority> roles = new ArrayList<>();
        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            token = authHeader.substring(7); 
            username = jwtService.extractUsername(token);
            roles = jwtService.extractRoles(token);
            System.out.println(roles);
        } 
  
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            UserDetails userDetails = userService.loadUserByUsername(username); 
            if (jwtService.validateToken(token, userDetails)) { 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                SecurityContextHolder.getContext().setAuthentication(authToken); 
            } 
        } 
        filterChain.doFilter(request, response); 
    } 
} 