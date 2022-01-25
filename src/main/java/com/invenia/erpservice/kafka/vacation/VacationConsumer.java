package com.invenia.erpservice.kafka.vacation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.kafka.common.Topic;
import com.invenia.erpservice.mattermost.AttachmentsItem;
import com.invenia.erpservice.mattermost.Message;
import com.invenia.erpservice.mattermost.Props;
import com.invenia.erpservice.user.UserEntity;
import com.invenia.erpservice.user.UserNotFoundException;
import com.invenia.erpservice.user.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationConsumer {

  private final UserService userService;

  @KafkaListener(topics = "erp_TPRWkEmpVacApp")
  public void vacationListener(String topicMessage) {
    Topic<VacationPayload> topic = new Topic<>();
    try {
      topic = new ObjectMapper().readValue(topicMessage, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }

    VacationPayload payload = topic.getPayload();
    UserEntity user = userService.getUserByEmpSeq(payload.getEmpSeq()).orElseThrow(UserNotFoundException::new);

    if (user.getDeptSeq() == 2039) { // IT 개발파트
      callMattermostAPI(payload, user.getUserName(), "wjowika97tbqxd31sgjmddqier");
    } else if (user.getDeptSeq() == 1681) { // IT 팀
      callMattermostAPI(payload, user.getUserName(), "35nctfyuzbyuj8hpu5qwnqmroh");
    }
  }

  private void callMattermostAPI(VacationPayload payload, String userName, String channelId) {
    String text = "";
    text += payload.getPrevUseDays().equals("AMNQ") ? "**[반차]** " : "";
    if (payload.getWkFrDate().equals(payload.getWkToDate())) {
      text += LocalDate.parse(payload.getWkFrDate(), DateTimeFormatter.BASIC_ISO_DATE);
    } else {
      text += LocalDate.parse(payload.getWkFrDate(), DateTimeFormatter.BASIC_ISO_DATE)
          + " ~ "
          + LocalDate.parse(payload.getWkToDate(), DateTimeFormatter.BASIC_ISO_DATE);
    }

    Message message = Message.builder()
        .channelId(channelId)
        .props(Props.builder()
            .attachment(AttachmentsItem.builder()
                .title(userName + "님의 새로운 휴가 일정이 등록되었습니다.")
                .text(text)
                .footer("KSystem ver.5 Genuine")
                .footerIcon(
                    "https://slack.inveniacorp.com/files/m7wpn3sqo7d38b77wkjf4y35uy/public?h=uhctjlt6cQOdB22rd3gQklyPuj6zRAW8b7QPkSj6bNU")
                .build())
            .build())
        .build();

    WebClient webClient = WebClient.builder()
        .baseUrl("https://slack.inveniacorp.com")
        .clientConnector(new ReactorClientHttpConnector(
            HttpClient.create().wiretap(true)
        ))
        .build();

    String result = webClient
        .post()
        .uri("/api/v4/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .headers(header -> header.setBearerAuth("99uqgienebf5td4ztntemti4me"))
        .bodyValue(message)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    log.info("Mattermost API Response ==> {}", result);
  }
}
