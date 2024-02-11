package com.example.bootconfigdemo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHolderListener {

//данная конструкция передачи EventHolder в метод помогает спрингу понять, какой метод вызывать при публикации события
// РЕАЛИЗУЕМ ПУБЛИКАЦИЮ СОБЫТИЯ в EventQueueWorker, делать это будем в методе  startEventConsumer();
    @EventListener
    public void listen(EventHolder eventHolder){
        System.out.println("Call listen method");
        //распечатем ЕВЕНТ:
        System.out.println(eventHolder.getEvent());
    }
}
