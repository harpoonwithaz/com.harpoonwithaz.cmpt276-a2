package com.harpoonwithaz.cmpt276_a2.controllers;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.harpoonwithaz.cmpt276_a2.models.StaffRating;
import com.harpoonwithaz.cmpt276_a2.models.StaffRatingRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.harpoonwithaz.cmpt276_a2.models.RoleType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller 
public class StaffRatingController {

    @Autowired 
    private StaffRatingRepository staffRatingRepo;

    // List all ratings
    @GetMapping("ratings")
    public String getAllRatings(Model model) {
        System.out.println("Ratings endpoint called.");
        List<StaffRating> staffRatings = staffRatingRepo.findAll();

        model.addAttribute("rating", staffRatings);
        return "ratings/detail";
    }

    // Create new rating
    @PostMapping("ratings")
    public String postNewRating(
        @Valid @ModelAttribute("rating") StaffRating rating,
        BindingResult bindingResult,
        Model model)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("Error in postNewRating()");
            return "ratings/create";
        }

        // If valid set created at and updated at
        rating.setCreatedAt(Instant.now());
        rating.setUpdatedAt(Instant.now());

        if (rating.getRoleType() == null) {
            rating.setRoleType(RoleType.PROF); // default to prof it fails
        }

        staffRatingRepo.save(rating);

        return "redirect:/ratings";
    }
    
    // public String postNewRating(@RequestParam Map<String, String> rating, HttpServletResponse response, Model model) {
    //     System.out.println("ADD rating");

    //     String name = rating.get("name");
    //     System.out.println(name);
    //     String email = rating.get("email");
    //     // String role = rating.get("role");
    //     RoleType role = RoleType.PROF; // hard coded for now
    //     int clarity = Integer.parseInt(rating.get("clarity"));
    //     int niceness = Integer.parseInt(rating.get("niceness"));
    //     int knowledgeability = Integer.parseInt(rating.get("knowledgeability"));
    //     String comment = rating.get("comment");

    //     StaffRating newRating = new StaffRating(
    //         name,
    //         email,
    //         role,
    //         clarity,
    //         niceness,
    //         knowledgeability,
    //         comment,
    //         Instant.now(),
    //         Instant.now()
    //     );

    //     staffRatingRepo.save(newRating);
    //     response.setStatus(201);

    //     List<StaffRating> staffRatings = new ArrayList<>();
    //     staffRatings.add(newRating);

    //     model.addAttribute("rt", staffRatings);
    //     return "ratings/detail";
    // }
    

    @GetMapping("ratings/{id}")
    public String getRatingById(@PathVariable long id) {
        // TODO: Get rating from database for staff with id
        
        return new String();
    }
    
    
    // Show blank creation form
    @GetMapping("ratings/new")
    public String showCreateForm(Model model) {
        // Provide an empty object for Thymeleaf's form bindings
        model.addAttribute("rating", new StaffRating());
        return "ratings/create";
    }
}
