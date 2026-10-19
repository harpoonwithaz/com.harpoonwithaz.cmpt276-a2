package com.harpoonwithaz.cmpt276_a2.models;

public class DefaultProfile implements StaffMemberProfile {
    private final String name;

    public DefaultProfile(String name) {
        this.name = name;
    }

    @Override 
    public String displayTitle() {
        return name + " (Other)";
    }
}
