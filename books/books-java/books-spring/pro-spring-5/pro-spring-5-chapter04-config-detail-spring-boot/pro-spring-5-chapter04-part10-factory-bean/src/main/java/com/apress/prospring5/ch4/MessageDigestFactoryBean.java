package com.apress.prospring5.ch4;

import java.security.MessageDigest;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class MessageDigestFactoryBean implements FactoryBean<MessageDigest>, InitializingBean {
    
    private String algorithmName = "MD5";

    private MessageDigest messageDigest = null;

    public MessageDigest getObject() throws Exception {
	return messageDigest;
    }

    public Class<MessageDigest> getObjectType() {
	return MessageDigest.class;
    }

    // Remember that by setting the singleton attribute of the FactoryBean’s <bean> tag, you tell Spring 
    // about the singleton status of the FactoryBean itself, not the objects it is returning
    public boolean isSingleton() {
	return true;
    }

    public void afterPropertiesSet() throws Exception {
	messageDigest = MessageDigest.getInstance(algorithmName);
    }

    public void setAlgorithmName(String algorithmName) {
	this.algorithmName = algorithmName;
    }
}
