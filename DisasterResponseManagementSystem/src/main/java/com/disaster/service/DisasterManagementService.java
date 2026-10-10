package com.disaster.service;
import com.disaster.model.*;
import com.disaster.generic.ResourceManager;
import com.disaster.exception.*;
import com.disaster.util.PriorityCalculator;
import java.util.*;
public class DisasterManagementService {
    public final ResourceManager<Volunteer> volunteers=new ResourceManager<>();
    public final ResourceManager<EmergencyTeam> teams=new ResourceManager<>();
    public final ResourceManager<Vehicle> vehicles=new ResourceManager<>();
    public final ResourceManager<Food> foods=new ResourceManager<>();
    public final ResourceManager<Medicine> medicines=new ResourceManager<>();
    public final ResourceManager<Shelter> shelters=new ResourceManager<>();
    private final Map<String,EmergencyReport> reports=new LinkedHashMap<>();
    private final PriorityQueue<EmergencyReport> queue=new PriorityQueue<>();
    private final List<String> log=new ArrayList<>();
    private final NotificationService notifications=new NotificationService();
    private <T extends Identifiable> void unique(ResourceManager<T> m,T x,String type)throws DuplicateIdException {
        if(m.contains(x.getId()))throw new DuplicateIdException(type+" ID already exists");
        m.add(x);
        log.add(type+" added: "+x.getId());
    }
    public void addVolunteer(Volunteer x)throws DuplicateIdException {
        unique(volunteers,x,"Volunteer");
    }
    public void addTeam(EmergencyTeam x)throws DuplicateIdException {
        unique(teams,x,"Team");
    }
    public void addVehicle(Vehicle x)throws DuplicateIdException {
        unique(vehicles,x,"Vehicle");
    }
    public void addFood(Food x)throws DuplicateIdException {
        unique(foods,x,"Food");
    }
    public void addMedicine(Medicine x)throws DuplicateIdException {
        unique(medicines,x,"Medicine");
    }
    public void addShelter(Shelter x)throws DuplicateIdException {
        unique(shelters,x,"Shelter");
    }
    public EmergencyReport createReport(String reporter,String location,DisasterType type,int people,boolean med,boolean vul,boolean trapped)throws InvalidReportException {
        if(reporter.isBlank()||location.isBlank()||type==null||people<=0)throw new InvalidReportException("Enter reporter, location, type and positive affected count");
        String id="E"+String.format("%03d",reports.size()+1);
        EmergencyReport r=new EmergencyReport(id,reporter,location,type,people,med,vul,trapped);
        r.setPriority(PriorityCalculator.calculate(r));
        reports.put(id,r);
        queue.add(r);
        log.add(id+" reported; priority="+r.getPriority());
        return r;
    }
    public EmergencyReport next() {
        return queue.peek();
    }
    public List<EmergencyReport> reports() {
        return new ArrayList<>(reports.values());
    }
    private <T extends Assignable>T available(List<T> a) {
        return a.stream().filter(Assignable::isAvailable).findFirst().orElse(null);
    }
    public void process(String id)throws ResourceUnavailableException {
        EmergencyReport r=reports.get(id);
        if(r==null)throw new IllegalArgumentException("Emergency not found");
        Volunteer v=available(volunteers.getAll());
        EmergencyTeam t=available(teams.getAll());
        Vehicle vh=available(vehicles.getAll());
        Shelter sh=shelters.getAll().stream().filter(x->x.isAvailable()&&x.getAvailableSpaces()>0).findFirst().orElse(null);
        if(v==null||t==null||vh==null||sh==null)throw new ResourceUnavailableException("Need available volunteer, team, vehicle and shelter");
        v.setAvailable(false);
        t.setAvailable(false);
        vh.setAvailable(false);
        sh.setAvailable(false);
        r.assignVolunteer(v.getId());
        r.assignTeam(t.getId());
        r.assignVehicle(vh.getId());
        r.assignShelter(sh.getId());
        r.setStatus(EmergencyStatus.ASSIGNED);
        queue.remove(r);
        log.add(id+" assigned resources");
        notifications.sendAsync(v,"Assigned to "+id+" at "+r.getLocation());
        notifications.sendAsync(t,"Respond to "+id+" at "+r.getLocation());
    }
    public void progress(String id) {
        EmergencyReport r=require(id);
        r.setStatus(EmergencyStatus.IN_PROGRESS);
        log.add(id+" IN_PROGRESS");
    }
    public void resolve(String id) {
        EmergencyReport r=require(id);
        r.setStatus(EmergencyStatus.RESOLVED);
        release(volunteers.get(r.getVolunteer()));
        release(teams.get(r.getTeam()));
        release(vehicles.get(r.getVehicle()));
        release(shelters.get(r.getShelter()));
        log.add(id+" RESOLVED");
    }
    private <T extends Assignable>void release(T x) {
        if(x!=null)x.setAvailable(true);
    }
    private EmergencyReport require(String id) {
        EmergencyReport r=reports.get(id);
        if(r==null)throw new IllegalArgumentException("Emergency not found");
        return r;
    }
    public List<Volunteer> getVolunteers() {
        return volunteers.getAll();
    }
    public List<EmergencyTeam> getTeams() {
        return teams.getAll();
    }
    public List<Vehicle> getVehicles() {
        return vehicles.getAll();
    }
    public List<Food> getFoods() {
        return foods.getAll();
    }
    public List<Medicine> getMedicines() {
        return medicines.getAll();
    }
    public List<Shelter> getShelters() {
        return shelters.getAll();
    }
    public List<String> log() {
        return new ArrayList<>(log);
    }
    public Queue<String> notifications() {
        return notifications.getHistory();
    }
    public long count(EmergencyStatus x) {
        return reports.values().stream().filter(r->r.getStatus()==x).count();
    }
    public int totalResources() {
        return vehicles.size()+foods.size()+medicines.size()+shelters.size();
    }
    public void demo()throws Exception {
        addVolunteer(new Volunteer("V01","Priya Sharma","900000001","Medical"));
        addVolunteer(new Volunteer("V02","Rahul Kumar","900000002","Rescue"));
        addTeam(new EmergencyTeam("T01","Rapid Rescue","900000101","Search and Rescue"));
        addTeam(new EmergencyTeam("T02","Medical Team","900000102","Medical"));
        addVehicle(new Vehicle("VH01","Rescue Van","Van"));
        addVehicle(new Vehicle("VH02","Rescue Boat","Boat"));
        addFood(new Food("F01","Rice Packets",500));
        addFood(new Food("F02","Water Bottles",1000));
        addMedicine(new Medicine("M01","First Aid Kits",100));
        addShelter(new Shelter("S01","Community Hall",250));
        addShelter(new Shelter("S02","Govt School",400));
        createReport("Demo","Velachery",DisasterType.FLOOD,12,true,true,true);
        createReport("Demo","Tambaram",DisasterType.CYCLONE,5,false,false,false);
    }
    public void close() {
        notifications.close();
    }
}
