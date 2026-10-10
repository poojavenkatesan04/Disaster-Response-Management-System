package com.disaster.generic;
import com.disaster.model.Identifiable;
import java.util.*;
public class ResourceManager<T extends Identifiable> {
    private final Map<String,T> data=new LinkedHashMap<>();
    public void add(T x) {
        data.put(x.getId(),x);
    }
    public T get(String id) {
        return data.get(id);
    }
    public T remove(String id) {
        return data.remove(id);
    }
    public boolean contains(String id) {
        return data.containsKey(id);
    }
    public int size() {
        return data.size();
    }
    public List<T> getAll() {
        return new ArrayList<>(data.values());
    }
}
