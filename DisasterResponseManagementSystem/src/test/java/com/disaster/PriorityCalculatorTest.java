package com.disaster;
import com.disaster.model.*;
import com.disaster.util.PriorityCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PriorityCalculatorTest {
    @Test void critical() {
        EmergencyReport r=new EmergencyReport("E1","A","X",DisasterType.EARTHQUAKE,25,true,true,true);
        assertEquals(PriorityLevel.CRITICAL,PriorityCalculator.calculate(r));
    }
    @Test void low() {
        EmergencyReport r=new EmergencyReport("E2","A","X",DisasterType.FLOOD,1,false,false,false);
        assertEquals(PriorityLevel.LOW,PriorityCalculator.calculate(r));
    }
}
