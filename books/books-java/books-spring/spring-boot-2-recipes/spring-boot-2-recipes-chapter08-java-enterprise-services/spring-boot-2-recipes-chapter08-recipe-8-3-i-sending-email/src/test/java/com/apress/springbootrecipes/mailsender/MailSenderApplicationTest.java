package com.apress.springbootrecipes.mailsender;

import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;

@SpringBootTest
public class MailSenderApplicationTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP) //
		    .withConfiguration(GreenMailConfiguration.aConfig().withUser("spring-boot-2-recipes@apress.com", "1234")) //
//		    .withConfiguration(GreenMailConfiguration.aConfig()) //
		    .withPerMethodLifecycle(false);

    @AfterEach
    public void cleanUp() {
	greenMail.reset();
    }

    @Test
    public void shouldHaveSendMail() throws Exception {
	MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
	assertThat(receivedMessages).hasSize(1);
	assertThat(receivedMessages[0].getSubject()).isEqualTo("Status message");
	assertThat(receivedMessages[0].getRecipients(Message.RecipientType.TO)).contains(new InternetAddress("recipient@some.where"));
    }
}
