package com.invenia.erpservice.kafka.vacation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Date;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class VacationPayload {

  private Integer companySeq;
  private Integer empSeq;
  private Integer vacSeq;
  private Integer wkItemSeq;
  private String wkFrDate;
  private String wkToDate;
  private String prevUseDays;
  private String appDate;
  private String vacReason;
  private String crisisTel;
  private String telNo;
  private Integer accptEmpSeq;
  @JsonProperty("CCSeq")
  private Integer cCSeq;
  private String isHalf;
  private String isEnd;
  private String isReturn;
  private String returnReason;
  private String timeTerm;
  private Integer lastUserSeq;
  private Date lastDateTime;
  private String leaveName;
  private String isCC;
  private String wkFrTime;
  private String wkToTime;
  private String wkEmpName;
  private Object vacHour;
}
