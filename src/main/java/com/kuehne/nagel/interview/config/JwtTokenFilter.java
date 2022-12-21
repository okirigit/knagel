package com.kuehne.nagel.interview.config;

import java.io.IOException;
import java.security.SignatureException;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(1)
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
  
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
      this.jwtTokenProvider = jwtTokenProvider;
    }
  
  

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println(token);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
              Authentication auth = jwtTokenProvider.getAuthentication(token);
              SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
        
    }
  }
  
