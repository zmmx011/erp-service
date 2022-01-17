package com.invenia.erpservice.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<User> getUsers() {
    List<User> users = userRepository.findAll();
    log.info("Find User Count : {}", users.size());
    return users;
  }
}
