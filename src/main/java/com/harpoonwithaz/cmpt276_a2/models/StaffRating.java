// staff_ratings database table

package com.harpoonwithaz.cmpt276_a2.models;

import java.time.Instant;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="staff_ratings")
public class StaffRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // create a serial datatype for the ID
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Must be a valid email")
    private String email;

    @NotNull(message = "Please select a role")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @NotNull(message = "Clarity score required")
    @Min(value = 1, message = "Clarity must be at least 1")
    @Max(value = 10, message = "Clarity cannot exceed 10")
    private int clarity;

    @NotNull(message = "Niceness score required")
    @Min(value = 1, message = "Niceness must be at least 1")
    @Max(value = 10, message = "Niceness cannot exceed 10")
    private int niceness;

    @NotNull(message = "Knowledgeability score required")
    @Min(value = 1, message = "Knowledgeability must be at least 1")
    @Max(value = 10, message = "Knowledgeability cannot exceed 10")
    private int knowledgeability;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    private Instant createdAt;
    private Instant updatedAt;
    
    // Default constructor
    public StaffRating() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public RoleType getRoleType() { return roleType; }
    public void setRoleType(RoleType roleType) { this.roleType = roleType; }

    public Integer getClarity() { return clarity; }
    public void setClarity(Integer clarity) { this.clarity = clarity; }

    public Integer getNiceness() { return niceness; }
    public void setNiceness(Integer niceness) { this.niceness = niceness; }

    public Integer getKnowledgeability() { return knowledgeability; }
    public void setKnowledgeability(Integer knowledgeability) { this.knowledgeability = knowledgeability; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // StaffMemberProfile helper methods
    public StaffMemberProfile getProfile() {
        if (this.roleType == null) {
            return new DefaultProfile(this.name);
        }

        switch (this.roleType) {
            case PROF:
                return new ProfProfile(this.name);
            case TA:
                return new TaProfile(this.name);
            default:
                return new DefaultProfile(this.name);
        }
    }

    public double getScore() {
        // Return average
        return (niceness + clarity + knowledgeability) / 3.0;
    }
}
