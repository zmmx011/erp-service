package com.invenia.erpservice.user;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Optional<UserEntity> getUserByEmpSeq(Integer empSeq) {
    return userRepository.findByEmpSeq(empSeq);
  }

  public List<UserEntity> getUsers() {
    List<UserEntity> userEntities = userRepository.findAll();
    log.info("Find User Count : {}", userEntities.size());
    return userEntities;
  }

  public void saveUser(UserEntity userEntity) {
    userRepository.save(userEntity);
  }
}
