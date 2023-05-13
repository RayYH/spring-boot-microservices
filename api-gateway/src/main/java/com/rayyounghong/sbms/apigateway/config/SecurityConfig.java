package com.rayyounghong.sbms.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security Config.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  /**
   * Spring Security Filter Chain.
   *
   * @param http ServerHttpSecurity
   * @return SecurityWebFilterChain
   */
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf().disable()
        .authorizeExchange(
            exchanges -> exchanges
                .pathMatchers("/eureka/**").permitAll()
                .anyExchange().authenticated()
        )
        .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
    return http.build();
  }
}
