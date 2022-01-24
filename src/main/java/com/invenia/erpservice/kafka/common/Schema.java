package com.invenia.erpservice.kafka.common;

import java.util.List;
import lombok.Data;

@Data
public class Schema {

  private String name;
  private boolean optional;
  private String type;
  private List<FieldsItem> fields;
}
