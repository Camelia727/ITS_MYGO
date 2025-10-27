package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.Goal;
import com.example.MY_Goal_Optimizer.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserId(Long userId);
}
