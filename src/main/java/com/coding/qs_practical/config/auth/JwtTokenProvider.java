//package com.coding.qs_practical.config.auth;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import java.nio.file.attribute.UserPrincipal;
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Component
//public class JwtTokenProvider {
//
//	@Value("${app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${app.jwtExpirationInMs}")
//    private long jwtExpirationInMs;
//
//    public String generateToken(Authentication authentication) {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
//
//        return Jwts.builder()
//                .setSubject(Long.toString(userPrincipal.getId()))
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return Long.parseLong(claims.getSubject());
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        } catch (Exception ex) {
//            // Handle exception
//            return false;
//        }
//    }
//
//    public String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    public Authentication getAuthentication(String token, UserDetailsService userDetailsService) {
//        Long userId = getUserIdFromToken(token);
//        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserById(userId);
//        return new UsernamePasswordAuthenticationToken(userPrincipal, "", userPrincipal.getAuthorities());
//    }
//}
