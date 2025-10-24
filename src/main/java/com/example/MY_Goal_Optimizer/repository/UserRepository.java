package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // EXISTS
    Boolean existsByAccount(String account);
    Boolean existsByEmail(String email);

    // FIND
    Optional<User> findByAccount(String account);

}
