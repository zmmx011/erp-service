package com.invenia.erpservice.api.user;

import java.io.Serial;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -7630066735832452688L;
}
