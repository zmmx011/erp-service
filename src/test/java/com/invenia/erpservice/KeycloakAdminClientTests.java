package com.invenia.erpservice;

import com.invenia.erpservice.keycloak.KeycloakAdminService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(classes = ErpServiceApplication.class, properties = {"spring.cloud.config.discovery.enabled:false"})
class KeycloakAdminClientTests {

  @Autowired
  KeycloakAdminService keycloakAdminService;

  private UserRepresentation userRepresentation;

  @BeforeEach
  public void init() {
    String testNum = "901";
    // 유저 정보
    userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(testNum);
    userRepresentation.setFirstName(testNum);
    userRepresentation.setEmail(testNum + "@co.kr");
    userRepresentation.setEnabled(true);
    // 인증 정보
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setValue(testNum);
    userRepresentation.setCredentials(List.of(credentialRepresentation));
  }

  @Test
  void findUserByUsernameTest() {
    System.out.println(keycloakAdminService.getUserByUserName("test").get(0).getEmail());
  }

  @Test
  void createUserTest() {
    keycloakAdminService.createUser(userRepresentation);
  }

  @Test
  void updateUserTest() {
    userRepresentation.setFirstName("updated");
  }

  @Test
  void logic() {
    List<UserRepresentation> foundUsers = keycloakAdminService.getUserByUserName(userRepresentation.getUsername());

    if (foundUsers.isEmpty()) {
      keycloakAdminService.createUser(userRepresentation);
    } else {
      userRepresentation.setFirstName("updated");
      keycloakAdminService.updateUser(foundUsers.get(0).getId(), userRepresentation);
    }
  }

  @Test
  public void destroy() {
    List<UserRepresentation> foundUsers = keycloakAdminService.getUserByUserName(userRepresentation.getUsername());
    if (!foundUsers.isEmpty()) {
      keycloakAdminService.removeUser(foundUsers.get(0).getId());
    }
  }
}
