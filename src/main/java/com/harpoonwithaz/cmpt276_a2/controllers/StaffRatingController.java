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

    // Return detail view of list of ratings
    // TODO: Make this endpoint go to the summary view
    @GetMapping({"", "/", "ratings"})
    public String getAllRatings(Model model) {
        System.out.println("Ratings endpoint called.");
        List<StaffRating> staffRatings = staffRatingRepo.findAll();

        model.addAttribute("ratings", staffRatings);
        return "index";
    }

    // Return detail view of rating
    @GetMapping("ratings/{id}")
    public String getRatingById(@PathVariable("id") Long id, Model model, HttpServletResponse response) {
        // Retrieve rating from database
        Optional<StaffRating> rating = staffRatingRepo.findById(id);

        // No entry found in database with id
        if (rating.isEmpty()) {
            response.setStatus(404);
            return "error/404";
        }

        List<StaffRating> ratingList = new ArrayList<>();
        ratingList.add(rating.get());

        model.addAttribute("rating", ratingList);
        return "ratings/detail";
    }
    
    
    // Show blank creation form
    @GetMapping("ratings/new")
    public String getShowCreateForm(Model model) {
        // Provide an empty object for Thymeleaf's form bindings
        model.addAttribute("rating", new StaffRating());
        return "ratings/create";
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

    // Show edit rating form
    @GetMapping("ratings/{id}/edit")
    public String getEditRatingById(@PathVariable("id") Long id, Model model, HttpServletResponse response) {
        // Retrieve rating from database
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
    @PostMapping("ratings/{id}/edit")
    public String postEditRatingById(
        @PathVariable("id") Long id,
        @Valid @ModelAttribute("rating") StaffRating rating,
        BindingResult bindingResult,
        Model model,
        HttpServletResponse response)
    {
        // Validate user input
        if (bindingResult.hasErrors()) {
            System.out.println("Error in postEditRatingById()");
            return "ratings/edit"; // go back to the edit page and thymeleaf will give feedback
        }

        // Retrieve existing rating from database
        Optional<StaffRating> er = staffRatingRepo.findById(id);

        // No entry found in database with id
        if (er.isEmpty()) {
            response.setStatus(404);
            return "error/404";
        }

        StaffRating existingRating = er.get();

        // Update existing rating
        existingRating.setName(rating.getName());
        existingRating.setEmail(rating.getEmail());
        existingRating.setRoleType(rating.getRoleType());
        existingRating.setClarity(rating.getClarity());
        existingRating.setNiceness(rating.getNiceness());
        existingRating.setKnowledgeability(rating.getKnowledgeability());
        existingRating.setComment(rating.getComment());
        existingRating.setUpdatedAt(Instant.now());

        // Update database
        staffRatingRepo.save(existingRating);

        // Redirect to ratings view
        return "redirect:/ratings";
    }

    // Delete action
    @PostMapping("ratings/{id}/delete")
    public String postDeleteRatingById(@PathVariable("id") Long id, Model model, HttpServletResponse response) {
        Optional<StaffRating> rating = staffRatingRepo.findById(id);

        // No entry found in database with id
        if (rating.isEmpty()) {
            response.setStatus(404);
            return "error/404";
        }

        staffRatingRepo.deleteById(id);

        return "redirect:/ratings";
    }
}
