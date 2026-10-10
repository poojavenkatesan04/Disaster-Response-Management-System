package com.disaster;
import com.disaster.model.*;
import com.disaster.service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ServiceTest {
    @Test void report() {
        DisasterManagementService s=new DisasterManagementService();
        try {
            EmergencyReport r=s.createReport("P","Chennai",DisasterType.FLOOD,10,true,false,false);
            assertEquals(PriorityLevel.HIGH,r.getPriority());
        } catch(Exception e) {
            fail(e);
        } finally {
            s.close();
        }
    }
    @Test void assignment()throws Exception {
        DisasterManagementService s=new DisasterManagementService();
        s.demo();
        EmergencyReport r=s.next();
        s.process(r.getId());
        assertEquals(EmergencyStatus.ASSIGNED,r.getStatus());
        s.close();
    }
}
