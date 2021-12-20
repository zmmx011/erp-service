package com.invenia.erpservice.keycloak;

import java.util.Arrays;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

  private final Keycloak keycloak;
/*
  public UserRepresentation addUserWithCredentials(UserRepresentation userToAdd, CredentialRepresentation credentialRepresentation) {
    if (credentialRepresentation != null) {
      credentialRepresentation.setTemporary(false);
      credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
      userToAdd.setCredentials(Arrays.asList(credentialRepresentation));
    }

    Response response = usersResource.create(userToAdd);
    if (response != null) {
      log.info("Repsonse Code: " + response.getStatusInfo());
      if (response.getStatusInfo().equals(Response.Status.CREATED)) {
        log.info("Repsonse Location: " + response.getLocation());
      }
    }
    StringBuilder builder = new StringBuilder();
    if (usersResource != null) {
      usersResource.list()
          .stream()
          .map(ur -> new ImmutablePair<>(ur.getUsername(), ur.getId()))
          .filter(pair -> userToAdd.getUsername().equalsIgnoreCase(pair.getKey().toString()))
          .forEach(pair -> {
            log.info("{}:{} \n", pair.getKey(), pair.getValue());
            builder.append(pair.getValue().toString());
          });
    }
    String createdUserId = builder.toString();


    log.info("User created with userId: {} \n", userId);
    return findUserByUUID(userId);
  }
*/
}
