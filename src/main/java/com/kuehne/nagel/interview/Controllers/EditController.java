package com.kuehne.nagel.interview.Controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kuehne.nagel.interview.Models.City;
import com.kuehne.nagel.interview.Services.CityService;

@RestController
@RequestMapping("/view")
public class EditController {

   
    @Autowired
    private CityService cityService;
   
   

    //Retrieve a city using the _id field
    @GetMapping("/{id}")
    @CrossOrigin
    public  City  getCity(@PathVariable String id) {
        // find the city with the given id
        return cityService.findByObjectId(id);
    }


}

