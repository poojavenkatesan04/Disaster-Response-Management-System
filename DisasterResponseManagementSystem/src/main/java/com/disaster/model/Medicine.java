package com.disaster.model;
public class Medicine extends Resource {
    private int quantity;
    public Medicine(String i,String n,int q) {
        super(i,n);
        setQuantity(q);
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int q) {
        if(q<0)throw new IllegalArgumentException("Negative quantity");
        quantity=q;
    }
    public String getType() {
        return "Medicine";
    }
}
