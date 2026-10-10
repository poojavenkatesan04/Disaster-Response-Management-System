package com.disaster.model;
public enum PriorityLevel {
    LOW(1),MEDIUM(2),HIGH(3),CRITICAL(4);
    private final int rank;
    PriorityLevel(int r) {
        rank=r;
    }
    public int getRank() {
        return rank;
    }
}
