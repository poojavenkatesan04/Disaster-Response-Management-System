package com.disaster.model;
public class Shelter extends Resource {
    private int capacity,occupied;
    public Shelter(String i,String n,int c) {
        super(i,n);
        if(c<=0)throw new IllegalArgumentException("Capacity must be positive");
        capacity=c;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getOccupied() {
        return occupied;
    }
    public int getAvailableSpaces() {
        return capacity-occupied;
    }
    public void setOccupied(int o) {
        if(o<0||o>capacity)throw new IllegalArgumentException("Invalid occupancy");
        occupied=o;
    }
    public String getType() {
        return "Shelter";
    }
}
