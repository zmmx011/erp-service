package com.invenia.erpservice.keycloak.dto.user;

import lombok.Data;

@Data
public class Credential {
  private int createdDate;
  private String credentialData;
  private String id;
  private int priority;
  private String secretData;
  private boolean temporary;
  private String type;
  private String userLabel;
  private String value;
}
