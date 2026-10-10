package com.disaster.model;
public abstract class Resource implements Identifiable,Assignable {
    private final String id;
    private String name;
    private boolean available=true;
    protected Resource(String i,String n) {
        if(i==null||i.isBlank()||n==null||n.isBlank())throw new IllegalArgumentException("Resource ID and name required");
        id=i;
        name=n;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String n) {
        name=n;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean a) {
        available=a;
    }
    public abstract String getType();
}
