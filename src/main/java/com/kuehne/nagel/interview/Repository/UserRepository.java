package com.kuehne.nagel.interview.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kuehne.nagel.interview.Models.User;

public interface UserRepository extends MongoRepository<User, String> {
    // custom methods go here
    User findByUsername(String username);

  }

