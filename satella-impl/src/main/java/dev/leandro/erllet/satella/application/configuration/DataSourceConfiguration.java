package dev.leandro.erllet.satella.application.configuration;

import lombok.val;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ConditionalOnClass(JpaTransactionManager.class)
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource(@Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.url}") String url) {
        val dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       @Value("${spring.datasource.dialect:org.hibernate.dialect.MySQL5Dialect}") String dialect) {
        val managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(dataSource);
        managerFactoryBean.setPackagesToScan("dev.leandro.erllet");
        val vendorAdapter = new HibernateJpaVendorAdapter();
        managerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        val properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        managerFactoryBean.setJpaProperties(properties);
        return managerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        val transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Primary
    public Flyway flyway(@Autowired ResourceLoader resourceLoader, @Autowired DataSource dataSource) {
        val flyway = new Flyway(resourceLoader.getClassLoader());
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:sql_infra");
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
        return flyway;
    }

}
