/**
 * Logger as a Singelton Pattern which must be the only used logger in the project.
 * @author: Leo Kuhlmey
 */

package de.unihdzahn.config;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class Logging {
  @Getter
  private Logger LOGGER;

  private static Logging instance = new Logging();

  private Logging() {
    this.LOGGER = LoggerFactory.getLogger(Logger.class);
  }

  public static Logging Instance() {
    return instance;
  }
}
