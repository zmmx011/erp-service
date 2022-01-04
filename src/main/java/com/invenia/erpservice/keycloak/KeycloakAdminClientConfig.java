package com.invenia.erpservice.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class KeycloakAdminClientConfig {
  public static final String serverUrl = "https://sso.inveniacorp.com:8080/auth";
  public static final String realm = "service";
  public static final String clientId = "admin-cli";
  public static final String clientSecret = "GefnvC2flJEwe8FIm1YjreTjVZKQEFFb";


  public static Keycloak getInstance() {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .resteasyClient(
            new ResteasyClientBuilder()
                .connectionPoolSize(10).build()
        ).build();
  }

  public static String getConfig() {
    return "KeycloakConfig{" +
        "serverUrl='" + serverUrl + '\'' +
        ", realm='" + realm + '\'' +
        ", clientId='" + clientId + '\'' +
        ", clientSecret='" + clientSecret + '\'' +
        '}';
  }
}

