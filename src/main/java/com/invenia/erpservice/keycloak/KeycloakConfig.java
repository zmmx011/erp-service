package com.invenia.erpservice.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KeycloakConfig {

  @Bean
  public Keycloak makeInstance() {
    return Keycloak.getInstance(
        "https://sso.inveniacorp.com:8080/auth",
        "service",
        "admin",
        "admin",
        "admin-cli");
  }
}
