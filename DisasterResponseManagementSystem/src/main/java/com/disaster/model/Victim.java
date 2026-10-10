package com.disaster.model;
public class Victim extends Person {
    private int age;
    private boolean injured;
    public Victim(String i,String n,String p,int a,boolean x) {
        super(i,n,p);
        age=a;
        injured=x;
    }
    public int getAge() {
        return age;
    }
    public boolean isInjured() {
        return injured;
    }
    public String getRole() {
        return "Victim";
    }
}
