package com.invenia.erpservice.kafka.user.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserSchema {

  private String name;
  private boolean optional;
  private String type;
  private List<UserFieldsItem> fields;
}
