package com.coding.qs_practical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coding.qs_practical.config.auth.JwtAuthFilter;
import com.coding.qs_practical.service.UserInfoService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig	extends WebSecurityConfigurerAdapter{
    
    private JwtAuthFilter authFilter;
    
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.authFilter = jwtAuthFilter;
	}
    
    @Bean
    public UserDetailsService userDetailsService() { 
        return new UserInfoService(); 
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/users/**").hasAuthority("USER")
                .antMatchers("/api/users/**").hasRole("USER")
            .anyRequest().authenticated()
            .and()
//            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            .and()
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService()); 
        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
        return authenticationProvider; 
    } 
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    }
}
