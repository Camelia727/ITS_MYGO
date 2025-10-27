package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserId(Long userId);
    boolean existsById(Long id);
}
