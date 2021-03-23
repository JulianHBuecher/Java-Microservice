/**
 * WebSecurityConfiguration Class for defining the access rules for accessing the endpoints.
 *
 * @author Julian B&uuml;cher
 */

package de.unihdzahn.security;

import de.unihdzahn.security.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfigration {

  /**
   * Handling the Roles and Attributes for the Students, who wants to log-in
   */

  @Autowired
  public WebSecurityConfigration() { }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf()
        .disable()
        .authorizeExchange()
        .matchers(PathRequest.toStaticResources().atCommonLocations())
        .permitAll()
        .pathMatchers(HttpMethod.GET, "/students")
        .hasRole(Role.ASSISTENT.name())
        .pathMatchers(HttpMethod.GET, "/students/{id}")
        .hasRole(Role.ASSISTENT.name())
        .anyExchange()
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(dentalStudentsJwtAuthenticationConverter());

    return http.build();
  }
  /**
    * With mapping user information like roles we always have the choice between
    * getting the roles from JWT token payload or from the mapped local persistent user.
    * The following one takes the roles from the JWT token payload
    */

  @Bean
  public DentalStudentsJwtAuthenticationConverter dentalStudentsJwtAuthenticationConverter() {

    return new DentalStudentsJwtAuthenticationConverter();
  }
}
