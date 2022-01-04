package com.invenia.erpservice.kafka.user.dto;

import lombok.Data;

@Data
public class UserTopic {

  private UserSchema schema;
  private UserPayload payload;
}
