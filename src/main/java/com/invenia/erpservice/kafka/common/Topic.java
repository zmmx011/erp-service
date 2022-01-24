package com.invenia.erpservice.kafka.common;

import lombok.Data;

@Data
public class Topic<T> {

  private Schema schema;
  private T payload;
}
