/**
 * Implementation of Service which uses the StudentRepository to collect all students.
 *
 * @author Julian B&uuml;cher
 */

package de.unihdzahn.service;

import de.unihdzahn.config.Logging;
import de.unihdzahn.db.dao.StudentRepository;
import de.unihdzahn.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SuppressWarnings("checkstyle:SummaryJavadoc")
@Component
public class Service {

  private final StudentRepository studentRepository;
  @Autowired
  public Service(StudentRepository studentRepository) { this.studentRepository = studentRepository; }

  public Mono<Student> findById(UUID id) {
    return Mono.fromCallable(() -> studentRepository.findById(id))
      .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty))
      .doOnNext(booking ->
        Logging.Instance().getLOGGER().info(
          String.format("Service.findById(): %s", booking.toString())))
      .doOnError(error ->
        Logging.Instance().getLOGGER().error("Service.findbyId: Error: %s", error.toString()));
  }

  public Flux<Student> getAll() {
    return Flux.fromIterable(studentRepository.findAll())
            .doOnError(error ->
                    Logging.Instance().getLOGGER().error("Service.getAll: Error: %s",
                            error.toString()));
  }

}
