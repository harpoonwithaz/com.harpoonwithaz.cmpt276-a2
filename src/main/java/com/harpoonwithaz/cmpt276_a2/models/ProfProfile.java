package com.harpoonwithaz.cmpt276_a2.models;

public class ProfProfile implements StaffMemberProfile {
    private final String name;

    public ProfProfile(String name) {
        this.name = name;
    }

    @Override 
    public String displayTitle() {
        return "Professor " + name + " (Faculty)";
    }
}
