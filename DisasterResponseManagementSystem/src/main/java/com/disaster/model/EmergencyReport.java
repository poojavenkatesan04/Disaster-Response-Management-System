package com.disaster.model;
public class EmergencyReport implements Identifiable,Comparable<EmergencyReport> {
    private final String id,reporterName,location;
    private final DisasterType disasterType;
    private final int peopleAffected;
    private final boolean medicalNeed,vulnerable,trapped;
    private PriorityLevel priority;
    private EmergencyStatus status=EmergencyStatus.REPORTED;
    private String volunteer="-",team="-",vehicle="-",shelter="-";
    public EmergencyReport(String i,String r,String l,DisasterType d,int p,boolean m,boolean v,boolean t) {
        if(p<=0)throw new IllegalArgumentException("Affected count must be positive");
        id=i;
        reporterName=r;
        location=l;
        disasterType=d;
        peopleAffected=p;
        medicalNeed=m;
        vulnerable=v;
        trapped=t;
    }
    public String getId() {
        return id;
    }
    public String getReporterName() {
        return reporterName;
    }
    public String getLocation() {
        return location;
    }
    public DisasterType getDisasterType() {
        return disasterType;
    }
    public int getPeopleAffected() {
        return peopleAffected;
    }
    public boolean isMedicalNeed() {
        return medicalNeed;
    }
    public boolean isVulnerable() {
        return vulnerable;
    }
    public boolean isTrapped() {
        return trapped;
    }
    public PriorityLevel getPriority() {
        return priority;
    }
    public void setPriority(PriorityLevel p) {
        priority=p;
    }
    public EmergencyStatus getStatus() {
        return status;
    }
    public void setStatus(EmergencyStatus s) {
        status=s;
    }
    public String getVolunteer() {
        return volunteer;
    }
    public String getTeam() {
        return team;
    }
    public String getVehicle() {
        return vehicle;
    }
    public String getShelter() {
        return shelter;
    }
    public void assignVolunteer(String x) {
        volunteer=x;
    }
    public void assignTeam(String x) {
        team=x;
    }
    public void assignVehicle(String x) {
        vehicle=x;
    }
    public void assignShelter(String x) {
        shelter=x;
    }
    public int compareTo(EmergencyReport x) {
        return Integer.compare(x.priority.getRank(),priority.getRank());
    }
}
