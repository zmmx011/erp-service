package com.invenia.erpservice.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("erp-service/v1/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @GetMapping("/all-sync")
  public ResponseEntity<String> allSync() {
    log.info("allSync");
    ModelMapper modelMapper = new ModelMapper();
    userService.getUsers().forEach(user -> {
      log.info(String.valueOf(user));
      UserRepresentation userRepresentation = modelMapper.map(user, UserRepresentation.class);
      log.info(String.valueOf(userRepresentation));
    });
    return ResponseEntity.ok().body("");
  }
}
