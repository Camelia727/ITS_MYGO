package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByGoalId(Long goalId);

    @Query("SELECT t FROM Task t " +
            "JOIN t.goal g " +
            "JOIN g.user u " +
            "WHERE u.id = :userId " +
            "AND t.deadline IS NOT NULL " +
            "AND DATE(t.deadline) = :today")
    List<Task> findTasksByUserIdAndDeadlineIsToday(
            @Param("userId") Long userId,
            @Param("today") LocalDate today);
}
