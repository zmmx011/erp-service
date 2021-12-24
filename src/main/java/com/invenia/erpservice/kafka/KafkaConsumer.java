package com.invenia.erpservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.keycloak.KeycloakAdminService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

  private final KeycloakAdminService keycloakAdminService;

  @KafkaListener(topics = "erp_TCAUser")
  public void userTopicListener(String msg) {
    log.info("Kafka Message ===> {}", msg);

    Map<Object, Object> map = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    try {
      map = mapper.readValue(msg, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
  }
}
