package org.crashcourse;

import java.math.BigDecimal;

import org.crashcourse.domain.model.Receipt;
import org.crashcourse.infra.dto.ProcessDTO;
import org.crashcourse.messaging.MessageSender;
import org.crashcourse.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    public static void main(final String[] args) {
	SpringApplication.run(KafkaApplication.class, args);
    }
    
    @Autowired
    private ReceiptService notaService;
    
    @Autowired
    private MessageSender messageSender;
    
    @Override
    public void run(String... args) throws Exception {
	notaService.save(new Receipt("123456789", "31481812807", new BigDecimal(100)));
	
	messageSender.sendProcessMessageAsync(new ProcessDTO("12345679", "123456789"));
    }
}
