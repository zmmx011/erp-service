package com.invenia.erpservice.keycloak;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak")
@RefreshScope
@Data
public class KeycloakAdminConfig {

  private String authServerUrl;
  private String realm;
  private String clientId;
  private String clientSecret;
}
