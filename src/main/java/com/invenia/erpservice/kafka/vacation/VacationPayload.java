package com.invenia.erpservice.kafka.vacation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Date;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class VacationPayload {
  private Integer empSeq;
  private Integer vacSeq;
  private String wkItemName;
  private String wkFrDate;
  private String wkToDate;
  private String vacReason;
  private Date lastDateTime;
  private Integer isEnd;
}
