package com.disaster.service;
import com.disaster.model.Notifiable;
import java.util.concurrent.*;
import java.util.*;
public class NotificationService implements AutoCloseable {
    private final ExecutorService pool=Executors.newFixedThreadPool(2);
    private final Queue<String> history=new ConcurrentLinkedQueue<>();
    public void sendAsync(Notifiable n,String m) {
        pool.submit(()->history.add("To "+n.getDisplayName()+" ("+n.getContact()+"): "+m));
    }
    public Queue<String> getHistory() {
        return history;
    }
    public void close() {
        pool.shutdown();
    }
}
