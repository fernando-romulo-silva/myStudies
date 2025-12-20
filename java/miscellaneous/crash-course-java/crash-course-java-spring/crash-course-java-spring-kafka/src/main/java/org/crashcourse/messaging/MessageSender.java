package org.crashcourse.messaging;

import static org.crashcourse.infra.config.kafka.KafkaTopicConfig.PROCESS_TOPIC;

import org.crashcourse.infra.dto.ProcessDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    
    private final KafkaTemplate<String, Object> multiTypeKafkaTemplate;
    

    MessageSender(final KafkaTemplate<String, String> kafkaTemplate, 
		  final KafkaTemplate<String, Object> multiTypeKafkaTemplate) {
	super();
	this.kafkaTemplate = kafkaTemplate;
	this.multiTypeKafkaTemplate = multiTypeKafkaTemplate;
    }


    public void sendStringMessageAsync(final String msg) {
	final var future = kafkaTemplate.send(PROCESS_TOPIC, msg);

	future.whenComplete((result, ex) -> {
	    if (ex == null) {
		System.out.println("Sent message=[" + msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
	    } else {
		System.out.println("Unable to send message=[" + msg + "] due to : " + ex.getMessage());
	    }
	});
    }
    
    
    public void sendProcessMessageAsync(final ProcessDTO processDTO) {
	final var future = multiTypeKafkaTemplate.send(PROCESS_TOPIC, processDTO);

	future.whenComplete((result, ex) -> {
	    if (ex == null) {
		System.out.println("Sent message=[" + processDTO + "] with offset=[" + result.getRecordMetadata().offset() + "]");
	    } else {
		System.out.println("Unable to send message=[" + processDTO + "] due to : " + ex.getMessage());
	    }
	});
    }
    
}
