package com.harpoonwithaz.cmpt276_a2.controllers;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.harpoonwithaz.cmpt276_a2.models.StaffRating;
import com.harpoonwithaz.cmpt276_a2.models.RoleType;

@Controller 
public class StaffRatingController {
    // List all ratings
    @GetMapping("ratings")
    public String getAllRatings(Model model) {
        System.out.println("Ratings endpoint called.");
        
        // TODO: Get all ratings from database
        List<StaffRating> staffRatings = new ArrayList<>();
        staffRatings.add(new StaffRating(10, "Bobby Chan", "bobbychan@sfu.ca", RoleType.PROF, 
        10, 10, 10, "goat", Instant.now(), Instant.now()));
        staffRatings.add(new StaffRating(2, "TOBY D", "bobbychan@sfu.ca", RoleType.PROF, 
        10, 10, 10, "goat", Instant.now(), Instant.now()));

        model.addAttribute("rt", staffRatings);
        System.out.println(model.getAttribute("rt"));
        return "ratings/detail";
    }

    @GetMapping("ratings/{id}")
    public String getRatingById(@PathVariable long id) {
        // TODO: Get rating from database for staff with id
        
        return new String();
    }
    
    
    // Show blank creation form
    @GetMapping("ratings/new")
    public String newRating() {
        return "ratings/create";
    }
}
