/**
 * Based on the project reactor this handlerclass is reactive, and handles the incoming ServerRequest. On
 * the other hand its build the ServerReponse based on the result of {@link de.unihdzahn.service.BookingService}.
 *
 * @author Leo Kuhlmey
 * @author Julian B&uuml;cher
 * @author Maximiliane Ott
 */

package de.unihdzahn.handler;


import de.unihdzahn.config.Logging;
import de.unihdzahn.service.Service;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.*;


@Component
public class Handler {
  private Service service;

  public Handler(@NotNull Service service) {
    this.service = service;
  }

  public Mono<ServerResponse> findById(ServerRequest request) {
    UUID id = UUID.fromString(request.pathVariable("id"));
    return service.findById(id)
                          .flatMap(buchung -> ok().contentType(MediaType.APPLICATION_JSON)
                                                    .body(BodyInserters.fromObject(buchung)))
                          .switchIfEmpty(notFound().build());
  }

  public Mono<ServerResponse> getAll(ServerRequest request) {
    Logging.Instance().getLOGGER().info("BuchuHandler.getAll()");

    return service.getAll()
            .collectList()
            .flatMap(allStudents -> ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject((allStudents))))
            .switchIfEmpty(notFound().build());
  }
}
