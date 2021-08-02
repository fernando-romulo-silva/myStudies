package com.apress.prospring5.ch4;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;

// Using the ApplicationContextAware interface, it is possible for your beans to get a reference to the ApplicationContext instance that configured them. 
// The main reason this interface was created is to allow a bean to access Spring’s ApplicationContext in your application, for example, 
// to acquire other Spring beans programmatically, using getBean(). 

public class ShutdownHookBean implements ApplicationContextAware {
    private ApplicationContext ctx;

    /** @Implements {@link ApplicationContextAware#setApplicationContext(ApplicationContext)} } */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {

	if (ctx instanceof GenericApplicationContext) {
	    ((GenericApplicationContext) ctx).registerShutdownHook();
	}
    }
}
