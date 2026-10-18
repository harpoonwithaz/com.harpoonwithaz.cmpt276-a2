package com.harpoonwithaz.cmpt276_a2.controllers;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

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
            return "ratings/create"; // go back to the create page and thymeleaf will give feedback
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
    

    @GetMapping("ratings/{id}")
    public String getRatingById(@PathVariable("id") Long id, Model model, HttpServletResponse response) {
        // TODO: Get rating from database for staff with id
        System.out.println("id passed in");
        System.out.println(id);
        Optional<StaffRating> rating = staffRatingRepo.findById(id);

        if (rating.isEmpty()) {
            response.setStatus(404);
            return "error/404";
        }

        List<StaffRating> ratingList = new ArrayList<>();
        ratingList.add(rating.get());

        model.addAttribute("rating", ratingList);
        return "ratings/detail";
    }
    
    // Show edit rating form
    @GetMapping("ratings/edit/{id}")
    public String getEditRatingById(@PathVariable("id") Long id, Model model, HttpServletResponse response) {
        Optional<StaffRating> rating = staffRatingRepo.findById(id);

        // No entry found in database with id
        if (rating.isEmpty()) {
            response.setStatus(404);
            return "error/404";
        }

        model.addAttribute("rating", rating.get());
        return "ratings/edit";
    }

    // Edit existing rating
    @PostMapping("ratings/edit")
    public String postEditRatingById(
        @Valid @ModelAttribute("rating") StaffRating rating,
        BindingResult bindingResult,
        Model model)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("Error in postEditRatingById()");
            return "ratings/edit"; // go back to the edit page and thymeleaf will give feedback
        }

        System.out.println("id of postEditRatingById:");
        System.out.println(rating.getId());
        rating.setUpdatedAt(Instant.now());

        staffRatingRepo.save(rating);
        return "redirect:/ratings";
    }
    
    // Show blank creation form
    @GetMapping("ratings/new")
    public String getShowCreateForm(Model model) {
        // Provide an empty object for Thymeleaf's form bindings
        model.addAttribute("rating", new StaffRating());
        return "ratings/create";
    }
}
