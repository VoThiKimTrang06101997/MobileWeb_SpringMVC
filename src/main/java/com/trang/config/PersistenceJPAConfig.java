package com.trang.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:database.properties" })
@ComponentScan({ "com.trang" })
@EnableJpaRepositories(basePackages = "com.trang.repository")
public class PersistenceJPAConfig {

	private final String PROPERTY_DRIVER = "jdbc.driver";
	private final String PROPERTY_URL = "jdbc.url";
	private final String PROPERTY_USERNAME = "jdbc.user";
	private final String PROPERTY_PASSWORD = "jdbc.password";
	private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private final String PROPERTY_DIALECT = "hibernate.dialect";
	private final String PROPERTY_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	
    @Autowired
    private Environment env;

    public PersistenceJPAConfig() {
        super();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[] { "com.trang.entity" });

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        
        hibernateProperties.setProperty(PROPERTY_DIALECT, env.getProperty(PROPERTY_DIALECT));
        hibernateProperties.setProperty(PROPERTY_SHOW_SQL, env.getProperty(PROPERTY_SHOW_SQL));
        hibernateProperties.setProperty(PROPERTY_HBM2DDL_AUTO, env.getProperty(PROPERTY_HBM2DDL_AUTO));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache",
        		env.getProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache",
        		env.getProperty("hibernate.cache.use_query_cache"));
		
        return hibernateProperties;
    }
    
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName(env.getProperty(PROPERTY_DRIVER));
        dataSource.setUrl(env.getProperty(PROPERTY_URL));
        dataSource.setUsername(env.getProperty(PROPERTY_USERNAME));
        dataSource.setPassword(env.getProperty(PROPERTY_PASSWORD));
		
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}