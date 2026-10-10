package com.disaster.model;
public class Vehicle extends Resource {
    private String vehicleType;
    public Vehicle(String i,String n,String t) {
        super(i,n);
        vehicleType=t;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public String getType() {
        return "Vehicle";
    }
}
