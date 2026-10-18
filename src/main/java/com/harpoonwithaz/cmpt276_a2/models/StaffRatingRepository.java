package com.harpoonwithaz.cmpt276_a2.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRatingRepository extends JpaRepository<StaffRating, Long> {
    // StaffRating findById(long id);

}
