package com.invenia.erpservice.keycloak;


import java.util.List;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeycloakAdminService {

  private final RealmResource realmResource;

  public KeycloakAdminService(KeycloakAdminConfig config) {
    log.info("Keycloak Config ==> {}", config.toString());
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    cm.setMaxTotal(200); // Increase max total connection to 200
    cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
    ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);

    realmResource = KeycloakBuilder.builder()
        .serverUrl(config.getAuthServerUrl())
        .realm(config.getRealm())
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(config.getClientId())
        .clientSecret(config.getClientSecret())
        .resteasyClient(
            new ResteasyClientBuilder()
                .httpEngine(engine).build()
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

  public void syncUser(String id, String pw, String name, String mail, int status) {
    // 인증 정보
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setValue(pw);

    // 유저 정보
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(id);
    userRepresentation.setFirstName(name);
    userRepresentation.setEmail(mail);
    userRepresentation.setEnabled(status != 18004); // 18004 : 영구중지
    userRepresentation.setCredentials(List.of(credentialRepresentation));

    List<UserRepresentation> foundUsers = getUserByUserName(userRepresentation.getUsername());

    if (foundUsers.isEmpty()) {
      Response response = createUser(userRepresentation);
      log.info("{} User create.", userRepresentation.getUsername());
      log.info("Response |  Status: {} | Status Info: {}", response.getStatus(), response.getStatusInfo());
    } else {
      updateUser(foundUsers.get(0).getId(), userRepresentation);
      log.info("{} User update.", foundUsers.get(0).getUsername());
    }
  }
}
