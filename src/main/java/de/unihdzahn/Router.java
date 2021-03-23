/**
 * This functional routerfunction provides every route this service can handle. Nested routes has been used for more
 * overview in the code.
 *
 * @auhtor Leo Kuhlmey
 * @author Julian B&uuml;cher
 * @author Maximiliane Ott
 */

package de.unihdzahn;

import de.unihdzahn.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {
  private static String id = "/{id}";

  private static String concatpathparam = String.format("/{whatever}%s",id);

  @Bean
  public RouterFunction<ServerResponse> students(Handler handler) {
    return route()
            .path("/students", b1 -> b1
                    .nest(accept(APPLICATION_JSON), b2 -> b2
                            .GET("", handler::getAll)
                            .GET(id, handler::findById)))
            .build();
  }
}
