package com.invenia.erpservice;

import com.invenia.erpservice.keycloak.KeycloakService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(classes = ErpServiceApplication.class, properties = {"spring.cloud.config.discovery.enabled:false"})
class KeycloakAPITests {

  @Autowired
  KeycloakService keycloakService;

  @Test
  void findUserByUsernameTest() {
    System.out.println(keycloakService.getUserByUserName("test"));
  }

  @Test
  void createUserTest() {
    // 인증 정보
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setValue("test10");

    // 유저 정보
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername("test");
    userRepresentation.setFirstName("test");
    userRepresentation.setEmail("test10@co.kr");
    userRepresentation.setEnabled(true); // 18004 : 영구중지
    userRepresentation.setCredentials(List.of(credentialRepresentation));

    HttpStatus response = keycloakService.createUser(userRepresentation);
  }

  @Test
  void updateUserTest() {
    List<UserRepresentation> users = keycloakService.getUserByUserName("test");

    if (!users.isEmpty()) {
      UserRepresentation user = users.get(0);
      user.setFirstName("updatedName10");
      HttpStatus response = keycloakService.updateUser(user, user.getId());
    }
  }
}
