package de.amitc.springcloudstreamsnotifier.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amitc.springcloudstreamsnotifier.event.Operation;

/**
 * SourceController
 */
@RestController
@RequestMapping("/source")
@EnableBinding(MessageBinding.class)
public class SourceController {

    @Autowired
    ObjectMapper om;

    @Autowired
    @Qualifier("hello")
    private MessageChannel messageBinding;

    @PostMapping("/{message}")
    public CustomMessage create(@PathVariable String message) {

        CustomMessage msg = new CustomMessage(message);
        Operation op = new Operation("push_message", "notify", om.valueToTree(msg));
        
        Message<Operation> m = MessageBuilder.withPayload(op).setHeader(KafkaHeaders.MESSAGE_KEY, op.getObject().get("uuid")).build();
        messageBinding.send(m);
        return msg;
    }
}

interface MessageBinding {
    String OUTPUT = "hello";

    @Output(OUTPUT)
    MessageChannel messageBinding();
}

class CustomMessage {
    String text;

    public CustomMessage(String message) {
        this.text = message;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}