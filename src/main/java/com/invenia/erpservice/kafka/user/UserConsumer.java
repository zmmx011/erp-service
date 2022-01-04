package com.invenia.erpservice.kafka.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.kafka.user.dto.UserPayload;
import com.invenia.erpservice.kafka.user.dto.UserTopic;
import com.invenia.erpservice.keycloak.KeycloakService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer extends AbstractConsumerSeekAware {

  private final KeycloakService keycloakService;

  @KafkaListener(topics = "erp_TCAUser")
  public void consumeTCAUser(String message) {
    log.debug("Kafka Message ===> {}", message);

    UserTopic userTopic = new UserTopic();
    try {
      userTopic = new ObjectMapper().readValue(message, UserTopic.class);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }

    // Topic 본문
    UserPayload payload = userTopic.getPayload();

    // 인증 정보
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setValue(payload.getLoginPwd());

    // 유저 정보
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(payload.getUserId());
    userRepresentation.setFirstName(payload.getUserName());
    userRepresentation.setEmail(payload.getPwdMailAdder());
    userRepresentation.setEnabled(payload.getLoginStatus() != 18004); // 18004 : 영구중지
    userRepresentation.setCredentials(List.of(credentialRepresentation));

    List<UserRepresentation> foundUsers = keycloakService.getUserByUserName(userRepresentation.getUsername());

    if (foundUsers.isEmpty()) {
      HttpStatus response = keycloakService.createUser(userRepresentation);
      log.info("Create User Response ==> {}", response);
    } else {
      HttpStatus response = keycloakService.updateUser(userRepresentation, foundUsers.get(0).getId());
      log.info("Update User Response ==> {}", response);
    }
  }
}
