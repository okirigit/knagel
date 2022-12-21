package com.kuehne.nagel.interview.Controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kuehne.nagel.interview.Models.City;
import com.kuehne.nagel.interview.Repository.CityRepository;

@RestController
@RequestMapping("/search")

public class SearchController {

    private final CityRepository cityRepository;

    public SearchController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    @Cacheable("cities")
    @CrossOrigin
    public List<City> search(@RequestParam("q") String query) {
        //filter the results to only show 5 elements. Use a second array to store thr top 5 results
        List<City> res = new ArrayList<>();
        Iterator c = cityRepository.findByNameLike(query).listIterator();
        int i = 5;

        while(i > 0 && c.hasNext()){
            City x = (City) c.next();
        //Basic filter function to limit the results to the user
        res.add(x);
        i--;
            
           
        }

        return res;
    }
}

