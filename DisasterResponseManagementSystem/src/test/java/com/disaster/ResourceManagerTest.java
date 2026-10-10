package com.disaster;
import com.disaster.generic.*;
import com.disaster.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ResourceManagerTest {
    @Test void genericManager() {
        ResourceManager<Food> m=new ResourceManager<>();
        Food f=new Food("F1","Rice",10);
        m.add(f);
        assertEquals(f,m.get("F1"));
        assertEquals(1,m.size());
    }
}
