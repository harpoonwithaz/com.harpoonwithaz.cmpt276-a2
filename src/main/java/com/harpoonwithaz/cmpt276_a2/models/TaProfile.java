package com.harpoonwithaz.cmpt276_a2.models;

public class TaProfile implements StaffMemberProfile {
    private final String name;

    public TaProfile(String name) {
        this.name = name;

    }

    @Override 
    public String displayTitle() {
        return "TA " + name + " (Assistant)";
    }
}
