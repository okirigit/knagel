package com.kuehne.nagel.interview.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kuehne.nagel.interview.Models.User;
import com.kuehne.nagel.interview.Repository.UserRepository;

@Service
public class UserAuth implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    // ...
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        com.kuehne.nagel.interview.Models.User user = userRepository.findByUsername(userName);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        user.getAuthorities()
          .forEach(role -> {
              grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
          });

          return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

}
