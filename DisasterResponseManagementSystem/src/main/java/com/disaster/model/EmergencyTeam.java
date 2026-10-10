package com.disaster.model;
public class EmergencyTeam extends Person implements Assignable {
    private String specialty;
    private boolean available=true;
    public EmergencyTeam(String i,String n,String p,String s) {
        super(i,n,p);
        specialty=s;
    }
    public String getSpecialty() {
        return specialty;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean a) {
        available=a;
    }
    public String getRole() {
        return "Emergency Team";
    }
}
