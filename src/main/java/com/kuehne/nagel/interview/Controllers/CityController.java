package com.kuehne.nagel.interview.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kuehne.nagel.interview.Models.City;
import com.kuehne.nagel.interview.Services.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

   
    @Autowired
    private CityService cityService;
   
    @GetMapping
    @CrossOrigin
    public Page<City> getCities(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        // retrieve paginated list of cities from the database using the default 
        //10 as number of cities in one page and page 0 as the default page
        return cityService.findAll(PageRequest.of(page, size));
    }   
}

