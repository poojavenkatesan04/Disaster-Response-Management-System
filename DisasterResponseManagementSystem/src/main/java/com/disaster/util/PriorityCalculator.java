package com.disaster.util;
import com.disaster.model.*;
public final class PriorityCalculator {
    private PriorityCalculator() {
    }
    public static PriorityLevel calculate(EmergencyReport r) {
        int s=0;
        s+=r.getPeopleAffected()>=20?4:r.getPeopleAffected()>=10?3:r.getPeopleAffected()>=5?2:1;
        if(r.isMedicalNeed())s+=3;
        if(r.isVulnerable())s+=2;
        if(r.isTrapped())s+=3;
        if(r.getDisasterType()==DisasterType.EARTHQUAKE)s+=2;
        else if(r.getDisasterType()==DisasterType.CYCLONE)s++;
        return s>=8?PriorityLevel.CRITICAL:s>=6?PriorityLevel.HIGH:s>=4?PriorityLevel.MEDIUM:PriorityLevel.LOW;
    }
}
