package de.amitc.springcloudstreamsnotifier.binder;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import de.amitc.springcloudstreamsnotifier.event.Operation;

/**
 * KafkaBinder
 */
@EnableBinding(Processor.class)
public class KafkaBinderProcessor {

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Operation handle(Operation operation) {
        System.out.println("Received: " + operation);
        return operation;
    }
}