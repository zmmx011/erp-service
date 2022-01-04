package com.invenia.erpservice.keycloak;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KeycloakService {

  private final WebClient webClient;

  public List<UserRepresentation> getUserByUserName(String userName) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/users")
            .queryParam("username", userName)
            .queryParam("exact", "true")
            .build()
        )
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<UserRepresentation>>() {})
        .block();
  }

  public HttpStatus createUser(UserRepresentation user) {
    WebClient.ResponseSpec response = webClient
        .post()
        .uri("/users")
        .body(Mono.just(user), UserRepresentation.class)
        .retrieve();

    return Optional.of(Objects.requireNonNull(response.toBodilessEntity().block()).getStatusCode()).get();
  }

  public HttpStatus updateUser(UserRepresentation user, String userId) {
    WebClient.ResponseSpec response = webClient
        .put()
        .uri("/users/" + userId)
        .body(Mono.just(user), UserRepresentation.class)
        .retrieve();

    return Optional.of(Objects.requireNonNull(response.toBodilessEntity().block()).getStatusCode()).get();
  }
}
