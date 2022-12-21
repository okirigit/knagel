package com.kuehne.nagel.interview.config;


import java.security.SignatureException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.kuehne.nagel.interview.Models.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

  @Value("${security.jwt.secret}")
  private String secret;

  @Value("${security.jwt.expiration}")
  private Long expiration;

  public String createToken(String userId) {
    Claims claims = Jwts.claims().setSubject(userId);
    //claims.put("authorities", roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
   claims.put("role", "ROLE_ALLOW_EDIT");
  Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration);
    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken;
    }
    return null;
  }

  public boolean validateToken(String token) throws SignatureException {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException ex) {
     // logger.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
     // logger.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
     // logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      //logger.error("JWT claims string is empty.");
    }
    return false;
  }
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    String userId = claims.getSubject();
    
    UserPrincipal userPrincipal = new UserPrincipal(claims.getId(), userId, userId, null);
    return new UsernamePasswordAuthenticationToken(userPrincipal, "", null);
  }

  
}
