package com.apress.springboot2recipes.jpa;

import static org.hibernate.cfg.AvailableSettings.CONNECTION_PROVIDER_DISABLES_AUTOCOMMIT;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.NON_CONTEXTUAL_LOB_CREATION;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class HibernateApplication {

    public static void main(String[] args) {
	SpringApplication.run(HibernateApplication.class, args);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {

	Properties properties = new Properties();
	properties.setProperty(DIALECT, "org.hibernate.dialect.PostgreSQL95Dialect");
	properties.setProperty(NON_CONTEXTUAL_LOB_CREATION, "true");
	properties.setProperty(CONNECTION_PROVIDER_DISABLES_AUTOCOMMIT, "true");

	LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	sessionFactoryBean.setDataSource(dataSource);
	sessionFactoryBean.setPackagesToScan("com.apress.springboot2recipes.jpa");
	sessionFactoryBean.setHibernateProperties(properties);
	return sessionFactoryBean;
    }

//  @Bean
//  public PlatformTransactionManager transactionManager(SessionFactory sf) {
//	  return new HibernateTransactionManager(sf);
//  }
}

@Component
class CustomerLister implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CustomerRepository customers;

    CustomerLister(CustomerRepository customers) {
	this.customers = customers;
    }

    @Override
    public void run(ApplicationArguments args) {

	customers.findAll().forEach(customer -> logger.info("{}", customer));
    }
}
