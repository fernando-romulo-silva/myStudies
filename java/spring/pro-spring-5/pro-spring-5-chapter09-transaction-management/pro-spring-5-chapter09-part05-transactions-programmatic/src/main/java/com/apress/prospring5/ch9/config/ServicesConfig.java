package com.apress.prospring5.ch9.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by iuliana.cosmina on 5/17/17.
 */
@Configuration
@ComponentScan(basePackages = "com.apress.prospring5.ch9")
public class ServicesConfig {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Bean
    @DependsOn("transactionManager")
    public TransactionTemplate transactionTemplate() {
	TransactionTemplate tt = new TransactionTemplate();
	tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
	tt.setTimeout(30);
	tt.setTransactionManager(transactionManager());
	return tt;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
	return new JpaTransactionManager(entityManagerFactory);
    }
}
