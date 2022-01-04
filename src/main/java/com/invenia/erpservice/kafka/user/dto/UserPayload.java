package com.invenia.erpservice.kafka.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class UserPayload {

  @JsonProperty("SMUserType")
  private int smUserType;
  private int userSeq;
  private String password3;
  private String password2;
  private int companySeq;
  private String password1;
  private int lastUserSeq;
  private String remark;
  private String isGroupWareUser;
  private int licenseType;
  private String userlimitDate;
  private int isLayoutAdmin;
  private int loginStatus;
  private int deptSeq;
  private int loginFailCnt;
  private String pwdType;
  private int loginType;
  private String pwdMailAdder;
  private String dsn;
  private String loginPwd;
  private int custSeq;
  private String userName;
  private String passHis;
  private int managementType;
  private int empSeq;
  private String loginDate;
  private Object loginFailFirstTime;
  private long lastDateTime;
  private String pwdChgDate;
  private String userId;
  private int userSecu;
  private int userType;
}
