package com.kuehne.nagel.interview.Services;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.kuehne.nagel.interview.Models.User;
import com.kuehne.nagel.interview.Repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  public User createUser(User user){
    userRepository.insert(user);
    return user;
  }
  public User editUser(User user){
    userRepository.save(user);
    return user;

  }
  public User getUser(String username){
    User user = userRepository.findByUsername(username);
   
    return user;

  }

  public UserDetails loadbyusername(String username){
    User user = userRepository.findByUsername(username);
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    user.getRoles()
      .forEach(role -> {
          grantedAuthorities.add(new SimpleGrantedAuthority(role
             ));
      });

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

  }
  public String authenticate(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
     return "invalid credentials";
    }

    return "Success";
    // generate and return JWT here
  }
}

