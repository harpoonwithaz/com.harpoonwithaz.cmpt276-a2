package com.harpoonwithaz.cmpt276_a2.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.harpoonwithaz.cmpt276_a2.models.RoleType;
import com.harpoonwithaz.cmpt276_a2.models.StaffRating;
import com.harpoonwithaz.cmpt276_a2.models.StaffRatingRepository;

@WebMvcTest(StaffRatingController.class)
class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffRatingRepository staffRatingRepo;

    @Test
    void getAllRatingsReturnsOkAndRatingsModelAttribute() throws Exception {
        StaffRating rating = validRating();
        when(staffRatingRepo.findAll()).thenReturn(List.of(rating));

        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("ratings", List.of(rating)));
    }

    @Test
    void getRatingByIdReturnsDetailViewAndRatingModelAttribute() throws Exception {
        StaffRating rating = validRating();
        rating.setId(1L);
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.of(rating));

        mockMvc.perform(get("/ratings/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/detail"))
                .andExpect(model().attribute("rating", List.of(rating)));
    }

    @Test
    void getRatingByIdWhenMissingReturnsNotFoundPage() throws Exception {
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/ratings/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    void getEditRatingByIdReturnsEditViewAndRatingModelAttribute() throws Exception {
        StaffRating rating = validRating();
        rating.setId(1L);
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.of(rating));

        mockMvc.perform(get("/ratings/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/edit"))
                .andExpect(model().attribute("rating", rating));
    }

    @Test
    void createRatingWithValidInputRedirectsToRatingsAndSavesRating() throws Exception {
        mockMvc.perform(post("/ratings")
                        .param("name", "Jane Doe")
                        .param("email", "jane.doe@example.com")
                        .param("roleType", RoleType.PROF.name())
                        .param("clarity", "8")
                        .param("niceness", "9")
                        .param("knowledgeability", "10")
                        .param("comment", "Excellent instructor"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"))
                .andExpect(flash().attributeCount(0));

        verify(staffRatingRepo).save(any(StaffRating.class));
    }

    @Test
    void createRatingWithInvalidInputReturnsCreateFormWithErrors() throws Exception {
        mockMvc.perform(post("/ratings")
                        .param("name", "")
                        .param("email", "not-an-email")
                        .param("roleType", RoleType.PROF.name())
                        .param("clarity", "11")
                        .param("niceness", "9")
                        .param("knowledgeability", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/create"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attributeHasFieldErrors("rating", "name", "email", "clarity"));

        verify(staffRatingRepo, never()).save(any(StaffRating.class));
    }

    @Test
    void editRatingWithValidInputUpdatesExistingRatingAndRedirects() throws Exception {
        StaffRating existingRating = validRating();
        existingRating.setId(1L);
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.of(existingRating));

        mockMvc.perform(post("/ratings/1/edit")
                        .param("name", "Updated Name")
                        .param("email", "updated@example.com")
                        .param("roleType", RoleType.TA.name())
                        .param("clarity", "7")
                        .param("niceness", "8")
                        .param("knowledgeability", "9")
                        .param("comment", "Updated comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));

        assertThatRatingMatches(existingRating, "Updated Name", "updated@example.com",
                RoleType.TA, 7, 8, 9, "Updated comment");
        verify(staffRatingRepo).save(existingRating);
    }

    @Test
    void editRatingWithInvalidInputReturnsEditFormWithErrors() throws Exception {
        mockMvc.perform(post("/ratings/1/edit")
                        .param("name", "")
                        .param("email", "not-an-email")
                        .param("roleType", RoleType.PROF.name())
                        .param("clarity", "0")
                        .param("niceness", "9")
                        .param("knowledgeability", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/edit"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attributeHasFieldErrors("rating", "name", "email", "clarity"));

        verify(staffRatingRepo, never()).save(any(StaffRating.class));
        verify(staffRatingRepo, never()).findById(1L);
    }

    @Test
    void deleteRatingWithExistingIdDeletesRatingAndRedirects() throws Exception {
        StaffRating rating = validRating();
        rating.setId(1L);
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.of(rating));

        mockMvc.perform(post("/ratings/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));

        verify(staffRatingRepo).deleteById(1L);
    }

    @Test
    void deleteRatingWhenMissingReturnsNotFoundPage() throws Exception {
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(post("/ratings/1/delete"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));

        verify(staffRatingRepo, never()).deleteById(1L);
    }

    @Test
    void getEditRatingByIdWhenMissingReturnsNotFoundPage() throws Exception {
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/ratings/1/edit"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    void editRatingWhenMissingReturnsNotFoundPage() throws Exception {
        when(staffRatingRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(post("/ratings/1/edit")
                        .param("name", "Updated Name")
                        .param("email", "updated@example.com")
                        .param("roleType", RoleType.TA.name())
                        .param("clarity", "7")
                        .param("niceness", "8")
                        .param("knowledgeability", "9"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    private void assertThatRatingMatches(
            StaffRating rating,
            String name,
            String email,
            RoleType roleType,
            int clarity,
            int niceness,
            int knowledgeability,
            String comment) {
        org.assertj.core.api.Assertions.assertThat(rating.getName()).isEqualTo(name);
        org.assertj.core.api.Assertions.assertThat(rating.getEmail()).isEqualTo(email);
        org.assertj.core.api.Assertions.assertThat(rating.getRoleType()).isEqualTo(roleType);
        org.assertj.core.api.Assertions.assertThat(rating.getClarity()).isEqualTo(clarity);
        org.assertj.core.api.Assertions.assertThat(rating.getNiceness()).isEqualTo(niceness);
        org.assertj.core.api.Assertions.assertThat(rating.getKnowledgeability()).isEqualTo(knowledgeability);
        org.assertj.core.api.Assertions.assertThat(rating.getComment()).isEqualTo(comment);
    }

    private StaffRating validRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Jane Doe");
        rating.setEmail("jane.doe@example.com");
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeability(10);
        rating.setComment("Excellent instructor");
        return rating;
    }
}