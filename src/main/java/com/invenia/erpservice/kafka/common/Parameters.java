package com.invenia.erpservice.kafka.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Parameters {

  private String scale;
  @JsonProperty("connect.decimal.precision")
  private String connectDecimalPrecision;
}
