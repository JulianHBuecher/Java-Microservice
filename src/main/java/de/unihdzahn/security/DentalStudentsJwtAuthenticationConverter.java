/**
 * The JWT Converter for checking the Roles in the delivered JWT tokens.
 *
 * @author Julian B&uuml;cher
 */

package de.unihdzahn.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The JWT Converter extract the roles out of the JWT token.
 */
@SuppressWarnings("checkstyle:SummaryJavadoc")
public class DentalStudentsJwtAuthenticationConverter
    implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
  /**
   * GROUP_CLAIM identifies the section in the JWT to extract the roles from.
   */
  private static final String GROUP_CLAIM = "groups";

  private static final String ROLE_PREFIX = "ROLE_";

  @Override
  public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
    Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
    return Mono.just(new UsernamePasswordAuthenticationToken(
        jwt.getClaims().get("preferred_username"),
        "n/a", authorities));
  }

  private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
    return this.getScopes(jwt).stream()
        .map(authority -> ROLE_PREFIX + authority.toUpperCase())
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("unchecked")
  private Collection<String> getScopes(Jwt jwt) {
    Object scopes = jwt.getClaims().get(GROUP_CLAIM);
    if (scopes instanceof Collection) {
      return (Collection<String>) scopes;
    }

    return Collections.emptyList();
  }
}
