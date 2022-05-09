package com.invenia.erpservice.api.user;

import com.invenia.erpservice.keycloak.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/erp-service/v1/users")
public class UserController {

  private final UserService userService;

  private final KeycloakAdminService keycloakAdminService;

  @GetMapping("/all-sync")
  public ResponseEntity<String> allSync() {
    userService.getUsers().forEach(user -> keycloakAdminService.syncUser(
        user.getUserId(),
        user.getLoginPwd(),
        user.getUserName(),
        user.getPwdMailAdder(),
        user.getLoginStatus()
    ));
    log.info("all-sync done.");
    return ResponseEntity.ok().build();
  }
}
