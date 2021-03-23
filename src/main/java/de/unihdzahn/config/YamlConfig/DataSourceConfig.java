/**
 * Reads configurationdata for the db-connection from the resources/application.yml.
 * @author: Leo Kuhlmey
 */

package de.unihdzahn.config.YamlConfig;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("datasource")
public class DataSourceConfig {
  @Setter
  @Getter
  private String driverClassName;

  @Setter
  @Getter
  private String url;

  @Setter
  @Getter
  private String username;

  @Setter
  @Getter
  private String password;
}
