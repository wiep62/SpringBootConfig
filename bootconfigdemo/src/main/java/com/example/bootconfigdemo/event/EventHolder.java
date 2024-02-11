package com.example.bootconfigdemo.event;

import com.example.bootconfigdemo.Event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter

public class EventHolder extends ApplicationEvent {
// в этом классе мы реализуем ApplicationEvent котторый требует определить конструкторс параметром Object source
    //так же передаем сюда наше собственное событие , которое будет получаться из очереди
    //для получения события генерируем геттер используя анннотацию ломбок @Getter
   //реализуем компонент за слушание этого события
    
    private final Event event;

    public EventHolder(Object source, Event event) {
        super(source);
        this.event = event;
    }
}
