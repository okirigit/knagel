package com.kuehne.nagel.interview.Repository;
import java.util.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kuehne.nagel.interview.Models.City;

public interface CityRepository extends MongoRepository<City, String> {
  //search by name Equivalent of SQL LIKE = '%name%'
    @Query("{ name:{ $regex: ?0, $options:'i' }}")
    List<City> findByNameLike(String name);

    
    Optional<City> findById(String id);


  }
