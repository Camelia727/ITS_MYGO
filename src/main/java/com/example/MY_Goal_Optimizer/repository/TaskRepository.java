package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
