package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByGoalId(Long goalId);
}
