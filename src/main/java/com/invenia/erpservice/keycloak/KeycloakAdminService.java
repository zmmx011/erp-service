package com.invenia.erpservice.keycloak;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeycloakAdminService {

  private final RealmResource realmResource;

  public KeycloakAdminService() {
    Keycloak keycloak = KeycloakConfig.getInstance();
    realmResource = keycloak.realm(KeycloakConfig.realm);
  }

  public List<UserRepresentation> findUserByUsername(String username) {
    UsersResource usersResource = realmResource.users();
    return usersResource.search(username, true);
  }

  public Response addUserWithCredentials(UserRepresentation user, CredentialRepresentation credential) {
    UsersResource usersResource = realmResource.users();
    if (credential != null) {
      credential.setTemporary(false);
      credential.setType(CredentialRepresentation.PASSWORD);
      user.setCredentials(List.of(credential));
    }

    return usersResource.create(user);
  }

  public UserRepresentation updateUserWithCredentials(UserRepresentation user, CredentialRepresentation credential) {
    UserResource userResource = realmResource.users().get(user.getId());

    UserRepresentation userBeforeUpdate = userResource.toRepresentation();
    if (credential != null) {
      // Define password credential
      credential.setTemporary(false);
      credential.setType(CredentialRepresentation.PASSWORD);
      // Set password credential
      userBeforeUpdate.setCredentials(Arrays.asList(credential));
    }
    //userBeforeUpdate = CustomMapper.customMap(user);
    userResource.update(userBeforeUpdate);
    log.info("User updated with basic infos ! \n");
    return user;
  }
}
