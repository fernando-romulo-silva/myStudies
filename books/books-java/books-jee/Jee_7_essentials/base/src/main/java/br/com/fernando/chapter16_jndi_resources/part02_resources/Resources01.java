package br.com.fernando.chapter16_jndi_resources.part02_resources;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.sql.DataSource;

public class Resources01 {

    @DataSourceDefinition( //
            name = "java:global/MyApp/MyDataSource", //
            className = "org.h2.jdbcx.JdbcDataSource", //
            url = "jdbc:h2:mem:test" //
    )
    @Stateless
    public static class DataSourceDefinitionHolder {
    }

    @Resource(lookup = "java:global/MyApp/MyDataSource")
    DataSource dataSource;

}
