package com.kuehne.nagel.interview.Controllers.Auth;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuehne.nagel.interview.Models.Role;
import com.kuehne.nagel.interview.Models.User;
import com.kuehne.nagel.interview.Services.UserService;

@RestController
@RequestMapping("/user")

public class CreateUser {

  @Autowired
  private UserService userService;

  

  @PostMapping("/register")
  @CrossOrigin
  public String create(@RequestBody User user) {
    if(userService.authenticate(user.getUsername(),user.getPassword()) != "Success"){
      User newUser = new User();
      newUser.setPassword(user.getPassword());
      newUser.setUsername(user.getUsername());
      Set<String> roles = user.getRoles();
      Iterator r = roles.iterator();
      while(r.hasNext()){
        newUser.addRole(r.next().toString());
      }
      userService.createUser(newUser);
      return "Success..User created";
    }
    return "Error. the user alrady exists";
  }
  @GetMapping("/hello")
  @CrossOrigin
  public String hello() {

return "hello ";
  }
}
