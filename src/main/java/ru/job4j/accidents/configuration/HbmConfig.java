package ru.job4j.accidents.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HbmConfig {

    public LocalSessionFactoryBean sessionFactory(@Value("${hibernate.dialect}") String dialect,
                                                  DataSource ds) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        sessionFactory.setPackagesToScan("ru.job4j.accidents.model");
        Properties cfg = new Properties();
        cfg.setProperty("hibernate.dialect", dialect);
        sessionFactory.setHibernateProperties(cfg);
        return sessionFactory;
    }

    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager htx(SessionFactory sf) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sf);
        return tx;
    }
}
