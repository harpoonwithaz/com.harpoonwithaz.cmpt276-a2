package com.harpoonwithaz.cmpt276_a2.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StaffRatingValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private StaffRating createValidRating() {
        StaffRating rating = new StaffRating();
        rating.setName("John Doe");
        rating.setEmail("john.doe@example.com");
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeability(10);
        rating.setComment("Great professor!");
        return rating;
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        StaffRating rating = createValidRating();
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEmailInvalid_thenValidationFails() {
        StaffRating rating = createValidRating();
        rating.setEmail("not-an-email");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void whenScoreOutOfRange_thenValidationFails() {
        StaffRating rating = createValidRating();
        rating.setClarity(15); // > 10

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("clarity")));
    }

    @Test
    void whenNameIsBlank_thenValidationFails() {
        StaffRating rating = createValidRating();
        rating.setName("   ");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }
}
