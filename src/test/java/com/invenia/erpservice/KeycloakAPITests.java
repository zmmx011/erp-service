package com.invenia.erpservice;

import com.invenia.erpservice.keycloak.KeycloakService;
import com.invenia.erpservice.keycloak.dto.user.Credential;
import com.invenia.erpservice.keycloak.dto.user.UserRepresentation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class KeycloakAPITests {

  @Autowired
  KeycloakService keycloakService;

  @Test
  void findUserByUsernameTest() {
    System.out.println(keycloakService.getUserByUserName("test"));
  }

  @Test
  void createUserTest() {
    UserRepresentation user = new UserRepresentation();
    user.setUsername("test10");
    user.setEnabled(true);
    user.setFirstName("test10");
    user.setEmail("test10@co.kr");

    List<Credential> credentials = new ArrayList<>();
    Credential credential = new Credential();
    credential.setValue("test10");
    credentials.add(credential);
    user.setCredentials(credentials);

    System.out.println(keycloakService.createUser(user));
  }

  @Test
  void updateUserTest() {
    List<UserRepresentation> users = keycloakService.getUserByUserName("test");

    if (!users.isEmpty()) {
      UserRepresentation user = users.get(0);
      user.setFirstName("updatedName10");
      System.out.println(keycloakService.updateUser(user, user.getId()));
    }
  }
}
