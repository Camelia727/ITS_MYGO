package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.goal.CreateGoalVO;
import com.example.MY_Goal_Optimizer.vo.goal.GoalVO;

import java.util.List;

public interface GoalService {

    List<GoalVO> getGoals(Long userId);

    GoalVO getGoal(Long goalId);

    void createGoal(Long userId, String title, String description, String category, Integer priority, String deadline);

    void updateGoal(Long goalId, String title, String description, String category, Integer status, Integer priority, String deadline);

    void deleteGoal(Long goalId);

}
