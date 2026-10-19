package com.harpoonwithaz.cmpt276_a2.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:staff-rating-test;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository staffRatingRepository;

    @Test
    void savingAndRetrievingRatingReturnsPersistedEntry() {
        StaffRating savedRating = staffRatingRepository.saveAndFlush(validRating());

        Optional<StaffRating> retrievedRating = staffRatingRepository.findById(savedRating.getId());

        assertThat(savedRating.getId()).isNotNull();
        assertThat(retrievedRating).isPresent();
        assertThat(retrievedRating.get().getName()).isEqualTo("John Doe");
        assertThat(retrievedRating.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(retrievedRating.get().getRoleType()).isEqualTo(RoleType.PROF);
        assertThat(retrievedRating.get().getClarity()).isEqualTo(8);
        assertThat(retrievedRating.get().getNiceness()).isEqualTo(9);
        assertThat(retrievedRating.get().getKnowledgeability()).isEqualTo(10);
        assertThat(retrievedRating.get().getComment()).isEqualTo("Great professor!");
    }

    @Test
    void deletingRatingRemovesEntry() {
        StaffRating savedRating = staffRatingRepository.saveAndFlush(validRating());

        staffRatingRepository.deleteById(savedRating.getId());
        staffRatingRepository.flush();

        assertThat(staffRatingRepository.findById(savedRating.getId())).isEmpty();
    }

    private StaffRating validRating() {
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
}
