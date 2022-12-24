package com.kuehne.nagel.interview.config;


import java.security.SignatureException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.kuehne.nagel.interview.Models.User;
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

  

  public String createToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getId());

    //claims.put("authorities", roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
   claims.put("role", "ROLE_ALLOW_EDIT");
  Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration);
    return Jwts.builder()
        .setSubject(user.getId())
        .setIssuedAt(now)
        .claim("roles", user.getRoles().toString())
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
  public UserDetails getUserDetails(String token) {
    User userDetails = new User();
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    String subject = (String) claims.get(Claims.SUBJECT);
    String roles = (String) claims.get("roles");
     
    roles = roles.replace("[", "").replace("]", "");
    String[] roleNames = roles.split(",");
     
    for (String aRoleName : roleNames) {
        userDetails.addRole(aRoleName);
    }
     
   // String[] jwtSubject = subject.split(",");
 
   // userDetails.setId(Integer.parseInt(jwtSubject[0]));
   // userDetails.setEmail(jwtSubject[1]);
 
    return userDetails;
}
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    String userId = claims.getSubject();
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get("authorities").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    UserPrincipal userPrincipal = new UserPrincipal(claims.getId(), userId, userId, null);
    return new UsernamePasswordAuthenticationToken(userPrincipal, "", null);
  }
  public void setAuthenticationContext(String token, HttpServletRequest request) {
    UserDetails userDetails = getUserDetails(token);
 
    UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
 
    authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
 
    SecurityContextHolder.getContext().setAuthentication(authentication);
}
  
}
