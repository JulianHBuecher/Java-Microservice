/**
 * Read the hibernate configuration from the resources/application.yml file.
 * @author: Leo Kuhlmey
 */

package de.unihdzahn.config.YamlConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("jpa.hibernate")
public class HibernatePropertiesConfig {
  @Setter
  @Getter
  private String dialect;

  @Setter
  @Getter
  private String jdbc_lob_non_contextual_creation;
}
