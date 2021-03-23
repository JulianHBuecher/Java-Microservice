/**
 * This class holds all Information for Hibernate for a valid bootstrapping. It has dependencys to
 * {@link de.unihdzahn.config.yamlconfig.DataSourceConfig} and
 * {@link de.unihdzahn.config.yamlconfig.HibernatePropertiesConfig}, which provides information from the application.yml
 * file.
 * @author Leo Kuhlmey
 */

package de.unihdzahn.db;

import de.unihdzahn.config.YamlConfig.DataSourceConfig;
import de.unihdzahn.config.YamlConfig.HibernatePropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.unihdzahn.db.dao") //, entityManagerFactoryRef = "entityManagerFactoryBean")
class PersistenceJPAConfig {
  private DataSourceConfig dataSourceConfig;
  private HibernatePropertiesConfig hibernatePropertiesConfig;

  private final String entityPackage = "de.unihdzahn.entity";

  @Autowired
  public PersistenceJPAConfig(DataSourceConfig dataSourceConfig, HibernatePropertiesConfig hibernatePropertiesConfig) {
    this.dataSourceConfig = dataSourceConfig;
    this.hibernatePropertiesConfig = hibernatePropertiesConfig;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
            new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPackagesToScan(new String[] { entityPackage });

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
    entityManagerFactoryBean.setJpaProperties(additionalProperties());

    return entityManagerFactoryBean;
  }

  @Bean
  public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(this.dataSourceConfig.getDriverClassName());
    dataSource.setUrl(this.dataSourceConfig.getUrl());
    dataSource.setUsername(this.dataSourceConfig.getUsername());
    dataSource.setPassword(this.dataSourceConfig.getPassword());
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

  final Properties additionalProperties() {
    final Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.dialect", this.hibernatePropertiesConfig.getDialect());
    hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation",
      this.hibernatePropertiesConfig.getJdbc_lob_non_contextual_creation());
    return hibernateProperties;
  }
}
