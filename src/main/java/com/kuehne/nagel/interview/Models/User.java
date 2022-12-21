package com.kuehne.nagel.interview.Models;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class User implements UserDetails{

  @Id
  private String id;


  private String username;

  private String password;

  private Set<String> roles = new HashSet<>();
 
  public void addRole(String role) {
      this.roles.add(role);
}
  public User(){}

  public User(String username, String password,String id,Set<String> roles){
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles  = roles;
  }

public String getPassword() {
    return password;
}
public String getUsername() {
    return username;
}
public String getId() {
    return id;
}
public Set<String> getRoles() {
    return roles;
}
public void setUsername(String name){
this.username = name;
}

public void setPassword(String pass){
    this.password = pass;
}
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
  // TODO Auto-generated method stub
  return null;
}
@Override
public boolean isAccountNonExpired() {
  // TODO Auto-generated method stub
  return false;
}
@Override
public boolean isAccountNonLocked() {
  // TODO Auto-generated method stub
  return false;
}
@Override
public boolean isCredentialsNonExpired() {
  // TODO Auto-generated method stub
  return false;
}
@Override
public boolean isEnabled() {
  // TODO Auto-generated method stub
  return false;
}


  // getters and setters
}

