package com.example.bootconfigdemo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
//todo этот компонент имеет в себе очередь и 2 метода ЭКЬЮ (enqueue - кладет события в очередь, а dequeue - получает и возвр-ет)
@Component
@ConditionalOnProperty("app.event-queue.enabled")
public class EventQueue {
    private final BlockingQueue<Event> queue = new LinkedBlockingQueue<>();
    public void enqueue(Event event){
        try {
            queue.put(event);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    //вычитка из очереди:
    public Event dequeue(){
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
