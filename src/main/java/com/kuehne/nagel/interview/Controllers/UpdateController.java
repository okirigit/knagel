package com.kuehne.nagel.interview.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kuehne.nagel.interview.Models.City;
import com.kuehne.nagel.interview.Services.CityService;

@RestController
@CrossOrigin
@RequestMapping("/update")
public class UpdateController {
    
    @Autowired
    private CityService cityService;

    @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
    @CrossOrigin
    @PostMapping
    
    public City update(@RequestBody City city) {
    
        // save the updated city
        return cityService.save(city);
    }
}
