package com.invenia.erpservice.kafka.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.kafka.user.dto.UserPayload;
import com.invenia.erpservice.kafka.user.dto.UserTopic;
import com.invenia.erpservice.keycloak.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer extends AbstractConsumerSeekAware {

  private final KeycloakAdminService keycloakAdminService;

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

    keycloakAdminService.syncUser(
        payload.getUserId(),
        payload.getLoginPwd(),
        payload.getUserName(),
        payload.getPwdMailAdder(),
        payload.getLoginStatus()
    );
  }
}
