package com.kuehne.nagel.interview.Models;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * This class represents the city entity and is annotated with @Entity and @Table to 
 * indicate that it is a JPA entity and should be mapped to a table in the database. 
 * The id field is annotated with @Id and @GeneratedValue to indicate that it is the 
 * primary key and should be generated automatically by the database. The name and photoUrl 
 * fields represent the name and photo URL of the city.
 * 
 * 
 */

 @Document(collection = "Cities")
public class City implements Comparable<City> {
  
   private String id;
   private String name;
   private String photo;

 

  public City(){}
  public City(String name, String photo, String id) {
    this.name = name;
    this.photo = photo;
    this.id = id;
  }
 
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }
  @Override
  public int compareTo(City arg0) {

    return this.name.compareTo(arg0.name);
  }

  
}

