package com.kuehne.nagel.interview.Models;


import org.springframework.data.annotation.Id;


public class Role  {
 
    
   
    private String id;

    private String role;
    public Role(){}
    public Role(String role,String id){
        this.role = role;
        this.id = id;
    }

 
}