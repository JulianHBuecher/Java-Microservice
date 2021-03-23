/**
 * This class start the application, and is the start point for the dockerfile.
 * @author Leo Kuhlmey
 */

package de.unihdzahn;

import de.unihdzahn.handler.Handler;
import de.unihdzahn.service.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Router.class, Handler.class, Service.class} )
public  class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}