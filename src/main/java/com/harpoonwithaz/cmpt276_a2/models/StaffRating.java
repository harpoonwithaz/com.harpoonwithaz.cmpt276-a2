package com.harpoonwithaz.cmpt276_a2.models;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name="staff_ratings")
public class StaffRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // create a serial datatype for the ID
    private Long id;
    private String name;
    private String email;
    // @Enumerated(EnumType.STRING) // store it as a string
    private RoleType roleType;
    private int clarity;
    private int niceness;
    private int knowledgeableScore;
    private String comment;
    private Instant timeCreated;
    private Instant timeUpdated;
    
    // Constructors
    public StaffRating() {}

    public StaffRating(String name, String email, RoleType roleType, int clarity, int niceness,
            int knowledgeableScore, String comment, Instant timeCreated, Instant timeUpdated) {
        this.name = name;
        this.email = email;
        this.roleType = roleType;
        this.clarity = clarity;
        this.niceness = niceness;
        this.knowledgeableScore = knowledgeableScore;
        this.comment = comment;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public int getClarity() {
        return clarity;
    }

    public int getNiceness() {
        return niceness;
    }

    public int getKnowledgeableScore() {
        return knowledgeableScore;
    }

    public String getComment() {
        return comment;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public Instant getTimeUpdated() {
        return timeUpdated;
    }
    
    
}
