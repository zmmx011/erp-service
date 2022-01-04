package com.invenia.erpservice.kafka.user.dto;

import lombok.Data;

@Data
public class UserFieldsItem {

  private String field;
  private boolean optional;
  private String type;
  private String name;
  private int version;
}
