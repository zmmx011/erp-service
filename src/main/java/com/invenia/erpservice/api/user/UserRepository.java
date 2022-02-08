package com.invenia.erpservice.api.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByEmpSeq(int empSeq);
}
