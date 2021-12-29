package com.invenia.erpservice.keycloak;

public class KCServerConnectionException extends RuntimeException {

  public KCServerConnectionException() {
    super(KeycloakConfig.getConfig());
  }
}
