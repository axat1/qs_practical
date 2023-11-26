package com.coding.qs_practical.config.auth;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService{

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; 
    public String generateToken(String userName, Collection<? extends GrantedAuthority> authorities) { 
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return createToken(claims, userName); 
    } 
  
    private String createToken(Map<String, Object> claims, String userName) { 
        return Jwts.builder() 
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) 
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact(); 
    } 
  
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
  
    public String extractUsername(String token) { 
        return extractClaim(token, Claims::getSubject); 
    } 
  
    public Date extractExpiration(String token) { 
        return extractClaim(token, Claims::getExpiration); 
    } 
    
    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
		List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
  
    @SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) { 
        return Jwts.parser()
                .setSigningKey(getSignKey()) 
                .parseClaimsJws(token) 
                .getBody(); 
    } 
  
    private Boolean isTokenExpired(String token) { 
        return extractExpiration(token).before(new Date()); 
    } 
  
    public Boolean validateToken(String token, UserDetails userDetails) { 
        final String username = extractUsername(token); 
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    } 
}
