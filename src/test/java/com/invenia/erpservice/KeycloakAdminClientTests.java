package com.invenia.erpservice;

import com.invenia.erpservice.keycloak.KeycloakConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@SpringBootTest
@ActiveProfiles("dev")
class KeycloakAdminClientTests {

  @Test
  void findUserByUsername() {
    Keycloak keycloak = KeycloakConfig.getInstance();
    RealmResource realmResource = keycloak.realm(KeycloakConfig.realm);
    UsersResource usersResource = realmResource.users();

    List<UserRepresentation> users = usersResource.search("test", true);

    Assert.notEmpty(users, "Collection must contain elements");
  }
}
