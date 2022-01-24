package com.invenia.erpservice.kafka.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.kafka.common.Topic;
import com.invenia.erpservice.keycloak.KeycloakAdminService;
import com.invenia.erpservice.user.UserEntity;
import com.invenia.erpservice.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer extends AbstractConsumerSeekAware {

  private final KeycloakAdminService keycloakAdminService;

  private final UserService userService;

  private final ModelMapper modelMapper;

  @KafkaListener(topics = "erp_TCAUser")
  public void userListener(String topicMessage) {
    Topic<UserPayload> topic = new Topic<>();
    try {
      topic = new ObjectMapper().readValue(topicMessage, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }

    // Topic 본문
    UserPayload payload = topic.getPayload();

    // keycloak
    keycloakAdminService.syncUser(
        payload.getUserId(),
        payload.getLoginPwd(),
        payload.getUserName(),
        payload.getPwdMailAdder(),
        payload.getLoginStatus()
    );

    // MSA DB
    userService.saveUser(modelMapper.map(payload, UserEntity.class));
  }
}
