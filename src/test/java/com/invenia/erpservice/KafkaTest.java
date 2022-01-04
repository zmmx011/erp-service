package com.invenia.erpservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invenia.erpservice.kafka.user.dto.UserTopic;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(classes = ErpServiceApplication.class, properties = {"spring.cloud.config.discovery.enabled:false"})
public class KafkaTest {


  @Test
  void convertJsonToUserTopicTest() {
    String json = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":true,\"field\":\"CompanySeq\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"UserSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"UserId\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"UserType\"},{\"type\":\"string\",\"optional\":true,\"field\":\"UserName\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"EmpSeq\"},{\"type\":\"string\",\"optional\":true,\"field\":\"LoginPwd\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Password1\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Password2\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Password3\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"CustSeq\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"DeptSeq\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"UserSecu\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LoginStatus\"},{\"type\":\"string\",\"optional\":true,\"field\":\"LoginDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PwdChgDate\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PassHis\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PwdMailAdder\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LoginFailCnt\"},{\"type\":\"string\",\"optional\":true,\"field\":\"PwdType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LoginType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"ManagementType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LastUserSeq\"},{\"type\":\"int64\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"LastDateTime\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Dsn\"},{\"type\":\"string\",\"optional\":true,\"field\":\"Remark\"},{\"type\":\"string\",\"optional\":true,\"field\":\"UserlimitDate\"},{\"type\":\"int64\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"LoginFailFirstTime\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"IsLayoutAdmin\"},{\"type\":\"string\",\"optional\":true,\"field\":\"IsGroupWareUser\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"SMUserType\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"LicenseType\"}],\"optional\":false,\"name\":\"_TCAUser\"},\"payload\":{\"CompanySeq\":1,\"UserSeq\":1851,\"UserId\":\"test11\",\"UserType\":1001,\"UserName\":\"테스트11\",\"EmpSeq\":1782,\"LoginPwd\":\"test11\",\"Password1\":\"\",\"Password2\":\"\",\"Password3\":\"\",\"CustSeq\":0,\"DeptSeq\":1681,\"UserSecu\":0,\"LoginStatus\":18001,\"LoginDate\":\"20220103\",\"PwdChgDate\":\"20211223\",\"PassHis\":\"\",\"PwdMailAdder\":\"test7@co.kr\",\"LoginFailCnt\":0,\"PwdType\":\"0\",\"LoginType\":0,\"ManagementType\":0,\"LastUserSeq\":1851,\"LastDateTime\":1641218992490,\"Dsn\":\"\",\"Remark\":\"\",\"UserlimitDate\":\"\",\"LoginFailFirstTime\":null,\"IsLayoutAdmin\":0,\"IsGroupWareUser\":\"0\",\"SMUserType\":1,\"LicenseType\":1112001}}";

    ObjectMapper mapper = new ObjectMapper();
    UserTopic userTopic = new UserTopic();

    try {
      userTopic = mapper.readValue(json, UserTopic.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    System.out.println(userTopic);
  }

}
