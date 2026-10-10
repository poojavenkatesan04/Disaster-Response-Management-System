package com.disaster.model;
public abstract class Person implements Identifiable,Notifiable {
    private final String id;
    private String name;
    private String phone;
    protected Person(String id,String name,String phone) {
        if(id==null||id.isBlank()||name==null||name.isBlank())throw new IllegalArgumentException("ID and name are required");
        this.id=id;
        this.name=name;
        this.phone=phone==null?"":phone;
    }
    public String getId() {
        return id;
    }
    public String getDisplayName() {
        return name;
    }
    public String getContact() {
        return phone;
    }
    public void setName(String n) {
        if(n==null||n.isBlank())throw new IllegalArgumentException("Name required");
        name=n;
    }
    public void setPhone(String p) {
        phone=p==null?"":p;
    }
    public abstract String getRole();
    public String toString() {
        return id+" - "+name+" ("+getRole()+")";
    }
}
