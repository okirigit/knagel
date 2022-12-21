package com.kuehne.nagel.interview.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kuehne.nagel.interview.Models.City;
import com.kuehne.nagel.interview.Repository.CityRepository;

@Service
public class CityService  {
  //We want to store the cities in a data structure to avoid fetching at every request
  List<City> cities = new ArrayList<City>();
  @Autowired
  private  CityRepository cityRepository;

  //method to fetch all cities
  public Page<City> findAll(Pageable pageable) {
    if(cities.size() < 1)
    cities = cityRepository.findAll();
    Collections.sort(cities);
    
    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startItem = currentPage * pageSize;
    List<City> pageOfCities = cities.subList(startItem, Math.min(startItem + pageSize, cities.size()));
    Page<City> cityPage = new PageImpl<City>(pageOfCities, pageable, cities.size());
    return cityPage;
  }

  //this will support the search function as the user will enter the name prefix
  public List<City> findByName(String name) {
     //TODO implement a suffix tree to represent the whole database and perform suffix search on it
    return cityRepository.findByNameLike(name);
  }
  //this will help in editing the name and photo since the first step is to fetch specific data of the city using ID
  public City findByObjectId(String id) {
    Optional<City> y = cityRepository.findById(id);
    City u = y.get();
    return u;
  }
  //helper function to save the new city details
  public City save(City city) {
    //update the city array with the updated values
    cityRepository.save(city);
    cities = cityRepository.findAll();
    return city ;
  }
  public City add(City city) {
    //Add a city
    cityRepository.insert(city);
    cities = cityRepository.findAll();
    return city;
  }

  //delete city
  public void deleteCityrById(String id) {
    cityRepository.deleteById(id);
}
}

