package com.invenia.erpservice.keycloak;


import java.util.List;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeycloakAdminService {

  private final RealmResource realmResource;

  public KeycloakAdminService(KeycloakAdminConfig config) {
    log.info("Keycloak Config ==> {}", config.toString());
    realmResource = KeycloakBuilder.builder()
        .serverUrl(config.getAuthServerUrl())
        .realm(config.getRealm())
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(config.getClientId())
        .clientSecret(config.getClientSecret())
        .resteasyClient(
            new ResteasyClientBuilder()
                .connectionPoolSize(10).build()
        )
        .build()
        .realm(config.getRealm());
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
