package com.invenia.erpservice.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class KeycloakClientConfig {

  @Bean
  ReactiveClientRegistrationRepository getRegistration() {
    ClientRegistration registration = ClientRegistration
        .withRegistrationId("keycloak")
        .tokenUri("https://sso.inveniacorp.com:8080/auth/realms/service/protocol/openid-connect/token")
        .clientId("admin-cli")
        .clientSecret("GefnvC2flJEwe8FIm1YjreTjVZKQEFFb")
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .build();
    return new InMemoryReactiveClientRegistrationRepository(registration);
  }

  @Bean(name = "keycloak")
  WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
        new ServerOAuth2AuthorizedClientExchangeFilterFunction(
            new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations,
                new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations)
            )
        );
    oauth.setDefaultClientRegistrationId("keycloak");
    return WebClient.builder()
        .baseUrl("https://sso.inveniacorp.com:8080/auth/admin/realms/service")
        .filter(oauth)
        .filter(logRequest())
        .build();
  }

  @Bean
  private static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{} = {}", name, value)));
      return Mono.just(clientRequest);
    });
  }
}

