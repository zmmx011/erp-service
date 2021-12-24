package com.invenia.erpservice.keycloak;

import org.keycloak.admin.client.Keycloak;

public class KeycloakConfig {

  public static final String serverUrl = "https://sso.inveniacorp.com:8080/auth";
  public static final String realm = "service";
  public static final String username = "admin";
  public static final String password = "admin";
  public static final String clientId = "admin-cli";

  public static Keycloak getInstance() {
    return Keycloak.getInstance(serverUrl, realm, username, password, clientId);
  }

  public static String getConfig() {
    return "KeycloakConfig{" +
        "serverUrl='" + serverUrl + '\'' +
        ", realm='" + realm + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", clientId='" + clientId + '\'' +
        '}';
  }
}
