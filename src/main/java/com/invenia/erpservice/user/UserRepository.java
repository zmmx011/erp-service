package com.invenia.erpservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
/*  Optional<User> findByEmail(String email);

  Optional<User> deleteByEmail(String email);*/
}
