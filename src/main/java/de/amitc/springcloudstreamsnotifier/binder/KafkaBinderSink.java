//Just for Demo: @StreamListener(Sink.INPUT)
//package de.amitc.springcloudstreamsnotifier.binder;
//
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//
//import de.amitc.springcloudstreamsnotifier.event.Operation;
//
//@EnableBinding(Sink.class)
//public class KafkaBinderSink {
//
//    //Just for Demo: @StreamListener(Sink.INPUT)
//    public void handle(Operation op) {
//        System.out.println("Revieced" + op);
//  }
//}