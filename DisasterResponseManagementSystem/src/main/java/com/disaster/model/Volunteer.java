package com.disaster.model;
public class Volunteer extends Person implements Assignable {
    private String skill;
    private boolean available=true;
    public Volunteer(String i,String n,String p,String s) {
        super(i,n,p);
        skill=s;
    }
    public String getSkill() {
        return skill;
    }
    public void setSkill(String s) {
        skill=s;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean a) {
        available=a;
    }
    public String getRole() {
        return "Volunteer";
    }
}
