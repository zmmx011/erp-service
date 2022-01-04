package com.invenia.erpservice.keycloak;


import java.util.List;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeycloakAdminService {

  private final RealmResource realmResource;

  public KeycloakAdminService() {
    Keycloak keycloak = KeycloakAdminClientConfig.getInstance();
    realmResource = keycloak.realm(KeycloakAdminClientConfig.realm);
  }

  public List<UserRepresentation> getUserByUserName(String username) {
    return realmResource.users().search(username, true);
  }

  public Response createUser(UserRepresentation user) {
    return realmResource.users().create(user);
  }

  public void updateUser(String id, UserRepresentation user) {
    realmResource.users().get(id).update(user);
  }

  public void removeUser(String id) {
    realmResource.users().get(id).remove();
  }
}
