package com.invenia.erpservice.user;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    ModelMapper modelMapper = new ModelMapper();
    AtomicInteger cnt = new AtomicInteger();
    userService.getUsers().forEach(user -> {
      UserRepresentation userRepresentation = modelMapper.map(user, UserRepresentation.class);
      log.info("{} - {} - {}", cnt.getAndIncrement(), user.getUserId(), userRepresentation.getAttributes());
    });
    return ResponseEntity.ok().body(String.valueOf(cnt));
  }
}
