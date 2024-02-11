package com.example.bootconfigdemo;

import com.example.bootconfigdemo.event.EventHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@RequiredArgsConstructor  //генерирует конструктор со свойствами для полей, которые указаны как final
//т.е. будет сгенерирован конструктор который будет принимать в себя      private final EventQueue eventQueue;
public class EventQueueWorker {
    //todo подключим БИН:
    private final EventQueue eventQueue;

    private final ApplicationEventPublisher publisher;

    @EventListener(ApplicationReadyEvent.class)
    public void startEventQueue(){ // он стартует 2 метода и чтобы он вызвался его нужно пометить аннотацией      @EventListener(ApplicationReadyEvent.class)
//todo  @EventListener - помечается на тех методах, которые отвечают за обработку событий, в данном случаем мы хотим чтобы startEventQueue вызвался когда наше приложение будет запущено
        //для этих целей в   @EventListener мы передаем класс ApplicationReadyEvent.class.
        //данный тип события генеррируется когда спринговый контекст полностью проинициализирован и приложение готово к работе.
        // мы используем этот обработчик для запуска поставщика и потребителя очереди

        //внутри этого метода будут вызоваться методы:
        startEventProducer();  //создает в бесконечном цикле ЕВЕНТЫ (1 раз в 3 секунды), для создания евента используется ПАТЕРН БИЛДЕР (реализует за нас ЛОМБОК)
        startEventConsumer(); //его задача вычитывать события из очереди  и выводить его в консоль

    }

      private void startEventProducer() {
        //стартуем поток:
        Thread eventProducerThread = new Thread(() ->{
            while (true){

            try {
                UUID id = UUID.randomUUID();
                Event event = Event.builder()
                        .id(id)
                        .payload("Payload for event " + id)
                        .build(); //сделаем билд - создадим компонет ЕВЕНТ:

                eventQueue.enqueue(event);
                Thread.sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            }
        });
        eventProducerThread.start();
    }

    private void startEventConsumer() {
        Thread eventConsumerThread = new Thread(() ->{
           while (true){
               Event event = eventQueue.dequeue();
            //   System.out.println(event);
               publisher.publishEvent(new EventHolder(this, event)); //указываем ИВЕНТ КОТОРЫЙ ПОЛУЧИЛИ ИЗ ОЧЕРЕДИ
           }
        });
        eventConsumerThread.start(); //стартуем его
    }
}
