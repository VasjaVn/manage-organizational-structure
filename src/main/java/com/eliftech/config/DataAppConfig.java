package com.eliftech.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
@EnableJpaRepositories("com.eliftech.repository")
@ComponentScan( basePackages = "com.eliftech",
        excludeFilters = @ComponentScan.Filter( type = FilterType.ANNOTATION, value = {Configuration.class}) )
public class DataAppConfig {

    private static final String ENTITY_PACKAGE[] = { "com.eliftech.entity" };

    private static final String PROP_DB_DRIVER = "db.driver";
    private static final String PROP_DB_URL = "db.url";
    private static final String PROP_DB_USERNAME = "db.username";
    private static final String PROP_DB_PASSWORD = "db.password";

    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( environment.getRequiredProperty(PROP_DB_DRIVER) );
        dataSource.setUrl( environment.getRequiredProperty(PROP_DB_URL) );
        dataSource.setUsername( environment.getRequiredProperty(PROP_DB_USERNAME) );
        dataSource.setPassword( environment.getRequiredProperty(PROP_DB_PASSWORD) );

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource( dataSource() );
        entityManagerFactoryBean.setJpaVendorAdapter( jpaVendorAdapter() );
        entityManagerFactoryBean.setJpaProperties( hibernateProperties() );
        entityManagerFactoryBean.setPackagesToScan( ENTITY_PACKAGE );

        return entityManagerFactoryBean;
    }



    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
        return transactionManager;
    }

    private AbstractJpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty( PROP_HIBERNATE_DIALECT, environment.getRequiredProperty(PROP_HIBERNATE_DIALECT) );
        properties.setProperty( PROP_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO) );
        return properties;
    }
}