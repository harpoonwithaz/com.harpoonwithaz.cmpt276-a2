package com.harpoonwithaz.cmpt276_a2.models;

import java.time.Instant;

public class StaffRating {
    private long id;
    private String name;
    private String email;
    private RoleType roleType;
    private int clarity;
    private int niceness;
    private int knowledgeableScore;
    private String comment;
    private Instant timeCreated;
    private Instant timeUpdated;
    // TODO: Created at and updated at
    
    // Constructors
    public StaffRating() {}

    public StaffRating(long id, String name, String email, RoleType roleType, int clarity, int niceness,
            int knowledgeableScore, String comment, Instant timeCreated, Instant timeUpdated) {
        this.id = id;
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

    public long getId() {
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
