// /**
//  * Intregration tests which is testing with rest-calls. The persistence-layer is not mocked, it tests what client
//  * will get if uses one of valid provided routes of {@link de.unihdzahn.Router}.
//  * @author Leo Kuhlmey
//  * @author Julian B&uuml;cher
//  */
//
// package de.unihdzahn.test;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
//
// import de.unihdzahn.Application;
// import de.unihdzahn.entity.Student;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.util.ArrayList;
//import java.util.Arrays;
// import java.util.List;
// import java.util.UUID;
//
// import static org.junit.Assert.assertEquals;
// import static org.springframework.http.MediaType.APPLICATION_JSON;
//
//
//
// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
// public class RestTest {
//   @Autowired
//   private WebTestClient webTestClient;
//
//   private static List<Student> studentList = new ArrayList<>(Arrays.asList(
//       new Student(UUID.fromString("d3498ffc-bea4-44bc-97c2-df9f664cd2be"), "12345",
//               "Leo","Kuhlmey","Männlich",21),
//       new Student(UUID.fromString("5dbfe800-0285-461b-975b-e179e6ef333d"), "23456",
//               "Julian","Bücher","Männlich",24),
//       new Student(UUID.fromString("a248df00-7807-4943-a689-4d4a5ea4c402"), "34567",
//               "Luis-Carl","Wettach","Männlich",24)
//   ));
//
//   @Test
//   public void alwaysSuccessful() {
//     assertEquals(true, true);
//   }
//
//   @Test
//   public void getById() throws Exception {
//     String uriWithPathVariableId = String.format("/students/%s", studentList.get(0).getHs_id());
//
//     webTestClient
//                   .get().uri(uriWithPathVariableId)
//                   .accept(APPLICATION_JSON)
//                   .exchange()
//                   .expectStatus().isOk()
//                   .expectBody(Student.class)
//                   .consumeWith(result ->
//                     assertThat(result.getResponseBody())
//                       .hasFieldOrPropertyWithValue("student", studentList.get(0).getStudent())
//                     );
//   }
//
//   @Test
//   public void getAll() throws Exception {
//     webTestClient
//                 .get().uri("/students")
//                 .accept(APPLICATION_JSON)
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectBody(ArrayList.class)
//                 .consumeWith(result ->
//                   assertThat(result.getResponseBody())
//                     .hasSize(6)
//                     .extracting("student")
//                     .contains(studentList.get(0).getStudent(),
//                         studentList.get(1).getStudent(),
//                         studentList.get(2).getStudent())
//                 );
//
//   }
// }
