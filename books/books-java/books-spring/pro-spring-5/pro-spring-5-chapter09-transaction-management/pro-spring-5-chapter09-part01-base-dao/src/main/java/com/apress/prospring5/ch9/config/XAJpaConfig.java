package com.apress.prospring5.ch9.config;

import static org.hibernate.cfg.AvailableSettings.AUTOCOMMIT;
import static org.hibernate.cfg.AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FLUSH_BEFORE_COMPLETION;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.JTA_PLATFORM;
import static org.hibernate.cfg.AvailableSettings.MAX_FETCH_DEPTH;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_FETCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * Created by iuliana.cosmina on 4/29/17.
 */
@Configuration
@EnableJpaRepositories
public class XAJpaConfig {

    private static Logger logger = LoggerFactory.getLogger(XAJpaConfig.class);

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSourceA() {
	try {
	    AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
	    dataSource.setUniqueResourceName("XADBMSA");
	    dataSource.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
	    dataSource.setXaProperties(xaAProperties());
	    dataSource.setPoolSize(1);
	    return dataSource;
	} catch (Exception e) {
	    logger.error("Populator DataSource bean cannot be created!", e);
	    return null;
	}
    }

    @Bean
    public Properties xaAProperties() {
	Properties xaProp = new Properties();
	xaProp.put("databaseName", "musicdb_a");
	xaProp.put("user", "prospring5_A");
	xaProp.put("password", "prospring5_A");
//	xaProp.put("useSSL", "false");
//	xaProp.put("allowPublicKeyRetrieval", "true");
	return xaProp;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSourceB() {
	try {
	    AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
	    dataSource.setUniqueResourceName("XADBMSB");
	    dataSource.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
	    dataSource.setXaProperties(xaBProperties());
	    dataSource.setPoolSize(1);
	    return dataSource;
	} catch (Exception e) {
	    logger.error("Populator DataSource bean cannot be created!", e);
	    return null;
	}
    }

    @Bean
    public Properties xaBProperties() {
	Properties xaProp = new Properties();
	xaProp.put("databaseName", "musicdb_b");
	xaProp.put("user", "prospring5_B");
	xaProp.put("password", "prospring5_B");
//	xaProp.put("useSSL", "false");
//	xaProp.put("allowPublicKeyRetrieval", "true");
	return xaProp;
    }

    @Bean
    public Properties hibernateProperties() {
	Properties hibernateProp = new Properties();
	hibernateProp.put("hibernate.transaction.factory_class", "org.hibernate.transaction.JTATransactionFactory");
	hibernateProp.put(JTA_PLATFORM, "com.atomikos.icatch.jta.hibernate4.AtomikosPlatform");
	// required by Hibernate 5
	hibernateProp.put(TRANSACTION_COORDINATOR_STRATEGY, "jta");
	hibernateProp.put(CURRENT_SESSION_CONTEXT_CLASS, "jta");

	hibernateProp.put(AUTOCOMMIT, false);
	hibernateProp.put(FLUSH_BEFORE_COMPLETION, false);
	hibernateProp.put(DIALECT, "org.hibernate.dialect.MySQL8Dialect"); // comment it if you use hibernate 4
	// this will work only if users/schemas are created first, use ddl.sql script for this
	hibernateProp.put(HBM2DDL_AUTO, "create-drop");
	hibernateProp.put(SHOW_SQL, true);
	hibernateProp.put(MAX_FETCH_DEPTH, 3);
	hibernateProp.put(STATEMENT_BATCH_SIZE, 10);
	hibernateProp.put(STATEMENT_FETCH_SIZE, 50);
	return hibernateProp;
    }

    @Bean
    public EntityManagerFactory emfA() {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setPackagesToScan("com.apress.prospring5.ch9.entities");
	factoryBean.setDataSource(dataSourceA());
	factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	factoryBean.setJpaProperties(hibernateProperties());
	factoryBean.setPersistenceUnitName("emfA");
	factoryBean.afterPropertiesSet();
	return factoryBean.getObject();
    }

    @Bean
    public EntityManagerFactory emfB() {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setPackagesToScan("com.apress.prospring5.ch9.entities");
	factoryBean.setDataSource(dataSourceB());
	factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	factoryBean.setJpaProperties(hibernateProperties());
	factoryBean.setPersistenceUnitName("emfB");
	factoryBean.afterPropertiesSet();
	return factoryBean.getObject();
    }
}
