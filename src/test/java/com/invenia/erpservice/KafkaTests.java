package com.invenia.erpservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.kafka.common.Topic;
import com.invenia.erpservice.kafka.user.UserPayload;
import com.invenia.erpservice.kafka.vacation.VacationPayload;
import com.invenia.erpservice.mattermost.AttachmentsItem;
import com.invenia.erpservice.mattermost.Message;
import com.invenia.erpservice.mattermost.Props;
import com.invenia.erpservice.user.UserEntity;
import com.invenia.erpservice.user.UserNotFoundException;
import com.invenia.erpservice.user.UserRepository;
import com.invenia.erpservice.user.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@ActiveProfiles("dev")
@SpringBootTest(classes = ErpServiceApplication.class, properties = {"spring.cloud.config.discovery.enabled:false"})

public class KafkaTests {
  // Test 실행 시 환경변수 ENCRYPT_KEY=damu 가 필요합니다.
  // todo 테스트 실행 시 환경변수 주입

  Logger log = LoggerFactory.getLogger(KafkaTests.class);

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  ModelMapper modelMapper;

  @Test
  void convertJsonToUserTopicTest() {
    String json = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":true,\"field\":\"CompanySeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"UserSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"UserId\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"UserType\"},{\"type\":\"string\",\"optional\":true,\"field\":\"UserName\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"EmpSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"LoginPwd\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Password1\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Password2\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Password3\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"CustSeq\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"DeptSeq\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"UserSecu\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LoginStatus\"},{\"type\":\"string\",\"optional\":true,\"field\":\"LoginDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PwdChgDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PassHis\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PwdMailAdder\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LoginFailCnt\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PwdType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LoginType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"ManagementType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LastUserSeq\"},{\"type\":\"int64\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"LastDateTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Dsn\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Remark\"},{\"type\":\"string\",\"optional\":true,\"field\":\"UserlimitDate\"},{\"type\":\"int64\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"LoginFailFirstTime\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"IsLayoutAdmin\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsGroupWareUser\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"SMUserType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LicenseType\"}],\"optional\":false,\"name\":\"_TCAUser\"},\"payload\":{\"CompanySeq\":1,\"UserSeq\":1795,\"UserId\":\"E1264\",\"UserType\":1001,\"UserName\":\"최범석\",\"EmpSeq\":1739,\"LoginPwd\":\"tnfdlekd1#\",\"Password1\":\"\",\"Password2\":\"\",\"Password3\":\"\",\"CustSeq\":0,\"DeptSeq\":2039,\"UserSecu\":0,\"LoginStatus\":18001,\"LoginDate\":\"20220112\",\"PwdChgDate\":\"20211229\",\"PassHis\":\"\",\"PwdMailAdder\":\"damu@inveniacorp.com\",\"LoginFailCnt\":0,\"PwdType\":\"0\",\"LoginType\":0,\"ManagementType\":0,\"LastUserSeq\":1195,\"LastDateTime\":1640821783247,\"Dsn\":\"\",\"Remark\":\"e1264\",\"UserlimitDate\":\"\",\"LoginFailFirstTime\":null,\"IsLayoutAdmin\":0,\"IsGroupWareUser\":\"0\",\"SMUserType\":1,\"LicenseType\":0}}";
    Topic<UserPayload> topic = new Topic<>();
    try {
      topic = new ObjectMapper().readValue(json, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    UserPayload payload = topic.getPayload();
    UserEntity userEntity = modelMapper.map(payload, UserEntity.class);

    log.info("Payload ===> {} ", payload.toString());

    log.info("Mapper ===> {} ", userEntity.toString());

    log.info("Entity ==> {}", userRepository.findByEmpSeq(payload.getEmpSeq()).toString());

    userRepository.save(userEntity);

    assertEquals("E1264", payload.getUserId());
  }

  @Test
  void convertJsonToVacationTopicTest() {
    String json = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"CompanySeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"EmpSeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"VacSeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"WkItemSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkFrDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkToDate\"},{\"type\":\"bytes\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Decimal\",\"version\":1,\"parameters\":{\"scale\":\"5\",\"connect.decimal.precision\":\"19\"},\"field\":\"PrevUseDays\"},{\"type\":\"string\",\"optional\":true,\"field\":\"AppDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"VacReason\"},{\"type\":\"string\",\"optional\":true,\"field\":\"CrisisTel\"},{\"type\":\"string\",\"optional\":true,\"field\":\"TelNo\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"AccptEmpSeq\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"CCSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsHalf\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsEnd\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsReturn\"},{\"type\":\"string\",\"optional\":true,\"field\":\"ReturnReason\"},{\"type\":\"string\",\"optional\":true,\"field\":\"TimeTerm\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LastUserSeq\"},{\"type\":\"int64\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"LastDateTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"LeaveName\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsCC\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkFrTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkToTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkEmpName\"},{\"type\":\"bytes\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Decimal\",\"version\":1,\"parameters\":{\"scale\":\"5\",\"connect.decimal.precision\":\"19\"},\"field\":\"VacHour\"}],\"optional\":false,\"name\":\"_TPRWkEmpVacApp\"},\"payload\":{\"CompanySeq\":1,\"EmpSeq\":1324,\"VacSeq\":45763,\"WkItemSeq\":24,\"WkFrDate\":\"20220124\",\"WkToDate\":\"20220124\",\"PrevUseDays\":\"AMNQ\",\"AppDate\":\"20220121\",\"VacReason\":\"개인사유로인한 반차\",\"CrisisTel\":null,\"TelNo\":null,\"AccptEmpSeq\":null,\"CCSeq\":0,\"IsHalf\":null,\"IsEnd\":null,\"IsReturn\":\"0\",\"ReturnReason\":null,\"TimeTerm\":null,\"LastUserSeq\":944,\"LastDateTime\":1642772513067,\"LeaveName\":null,\"IsCC\":\"0\",\"WkFrTime\":\"\",\"WkToTime\":\"\",\"WkEmpName\":\"\",\"VacHour\":null}}";
    Topic<VacationPayload> topic = new Topic<>();
    try {
      topic = new ObjectMapper().readValue(json, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    log.info(topic.getPayload().toString());
    assertEquals(45763, topic.getPayload().getVacSeq());
  }

  @Test
  void mattermostBotCall() {
    String json = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"CompanySeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"EmpSeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"VacSeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"WkItemSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkFrDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkToDate\"},{\"type\":\"bytes\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Decimal\",\"version\":1,\"parameters\":{\"scale\":\"5\",\"connect.decimal.precision\":\"19\"},\"field\":\"PrevUseDays\"},{\"type\":\"string\",\"optional\":true,\"field\":\"AppDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"VacReason\"},{\"type\":\"string\",\"optional\":true,\"field\":\"CrisisTel\"},{\"type\":\"string\",\"optional\":true,\"field\":\"TelNo\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"AccptEmpSeq\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"CCSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsHalf\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsEnd\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsReturn\"},{\"type\":\"string\",\"optional\":true,\"field\":\"ReturnReason\"},{\"type\":\"string\",\"optional\":true,\"field\":\"TimeTerm\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LastUserSeq\"},{\"type\":\"int64\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"LastDateTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"LeaveName\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsCC\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkFrTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkToTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"WkEmpName\"},{\"type\":\"bytes\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Decimal\",\"version\":1,\"parameters\":{\"scale\":\"5\",\"connect.decimal.precision\":\"19\"},\"field\":\"VacHour\"}],\"optional\":false,\"name\":\"_TPRWkEmpVacApp\"},\"payload\":{\"CompanySeq\":1,\"EmpSeq\":1735,\"VacSeq\":45779,\"WkItemSeq\":22,\"WkFrDate\":\"20220127\",\"WkToDate\":\"20220128\",\"PrevUseDays\":\"Aw1A\",\"AppDate\":\"20220124\",\"VacReason\":\"개인사유\",\"CrisisTel\":null,\"TelNo\":null,\"AccptEmpSeq\":null,\"CCSeq\":0,\"IsHalf\":null,\"IsEnd\":null,\"IsReturn\":\"0\",\"ReturnReason\":null,\"TimeTerm\":null,\"LastUserSeq\":1786,\"LastDateTime\":1643033542650,\"LeaveName\":null,\"IsCC\":\"0\",\"WkFrTime\":\"\",\"WkToTime\":\"\",\"WkEmpName\":\"\",\"VacHour\":null}}";
    Topic<VacationPayload> topic = new Topic<>();
    try {
      topic = new ObjectMapper().readValue(json, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }

    VacationPayload payload = topic.getPayload();
    UserEntity user = userService.getUserByEmpSeq(payload.getEmpSeq()).orElseThrow(UserNotFoundException::new);

    String text = "";
    text += payload.getPrevUseDays().equals("AMNQ") ? "**[반차]** " : "";
    if (payload.getWkFrDate().equals(payload.getWkToDate())) {
      text += LocalDate.parse(payload.getWkFrDate(), DateTimeFormatter.BASIC_ISO_DATE);
    } else {
      text += LocalDate.parse(payload.getWkFrDate(), DateTimeFormatter.BASIC_ISO_DATE)
          + " ~ "
          + LocalDate.parse(payload.getWkToDate(), DateTimeFormatter.BASIC_ISO_DATE);
    }

    log.info(text);

    Message message = Message.builder()
        .channelId("qi7yefa7kfyr5kj8nkguejna9o")
        .message("")
        .props(Props.builder()
            .attachment(AttachmentsItem.builder()
                .title(user.getUserName() + "님의 새로운 휴가 일정이 등록되었습니다.")
                .text(text)
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

    log.info(result);
  }
}
