package com.kuehne.nagel.interview.Controllers.Auth;

import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kuehne.nagel.interview.Models.User;
import com.kuehne.nagel.interview.Services.UserAuth;
import com.kuehne.nagel.interview.Services.UserService;
import com.kuehne.nagel.interview.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class LoginController {

  @Autowired
  private UserService userService;

 
  @Autowired
  private JwtTokenProvider jwtTokenProvider;


  @PostMapping("/login")
  @CrossOrigin
  public ResponseEntity<String> login(@RequestBody User userObject,  HttpServletRequest request, 
  HttpServletResponse response) {
    

    if( userService.authenticate(userObject.getUsername(), userObject.getPassword()) == "Success"){
        User user = userService.getUser(userObject.getUsername());
       
        String jwt = jwtTokenProvider.createToken(user);
        
        try {
          if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            jwtTokenProvider.setAuthenticationContext(jwt,request);
            response.addHeader("message", "User logged in successfully");
            response.addHeader("accessToken", jwt);
            response.addHeader("expiry", "1 day");


          //  userauth.loadUserByUsername(userObject.getUsername());
            return ResponseEntity.ok(jwt);
          }
      } catch (SignatureException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
       

       // System.out.println(auth.getDetails());
        return ResponseEntity.ok(jwt);
    }

    return ResponseEntity.ok("Login Failed");
  
  }
}
