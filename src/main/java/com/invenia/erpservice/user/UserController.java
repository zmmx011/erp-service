package com.invenia.erpservice.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-service/v1/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @PostMapping("/")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok().body(userService.createUser(user));
  }

  @PutMapping("/")
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    return ResponseEntity.ok().body(userService.updateUser(user));
  }

/*  @DeleteMapping("/{email}")
  public ResponseEntity<User> deleteUser(@PathVariable String email) {
    userService.deleteUser(email);
    return ResponseEntity.ok().build();
  }*/
}
