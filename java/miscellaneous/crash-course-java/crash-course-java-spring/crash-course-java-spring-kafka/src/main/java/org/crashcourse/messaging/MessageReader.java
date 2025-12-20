package org.crashcourse.messaging;

import static org.crashcourse.infra.config.kafka.KafkaTopicConfig.PROCESS_GROUP;
import static org.crashcourse.infra.config.kafka.KafkaTopicConfig.PROCESS_TOPIC;

import org.crashcourse.infra.config.logging.Loggable;
import org.crashcourse.infra.dto.ProcessDTO;
import org.crashcourse.service.BankSlipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Loggable
@Component
@KafkaListener(topics = PROCESS_TOPIC, groupId = PROCESS_GROUP)
public class MessageReader {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReader.class);
    
    private final BankSlipService bankSlipService;

    MessageReader(final BankSlipService bankSlipService) {
	this.bankSlipService = bankSlipService;
    }

    @KafkaHandler
    public void handleString(final String mgs) {
	LOGGER.info("Received String Message: {}", mgs);
    }

    @KafkaHandler
    public void handleProcess(final ProcessDTO process) {
	bankSlipService.execute(process);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(final Object object) {
	LOGGER.info("Received unknown Message: {}", object);
    }
}
