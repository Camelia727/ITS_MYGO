package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.service.GoalService;
import com.example.MY_Goal_Optimizer.vo.goal.GoalVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GoalServiceImpl
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:52
 */
@Service
public class GoalServiceImpl implements GoalService {

    @Override
    public List<GoalVO> getGoals(Long userId) {
        return List.of();
    }

    @Override
    public GoalVO getGoal(Long goalId) {
        return null;
    }

    @Override
    public void createGoal(Long userId, String title, String description, String category, Integer priority, String deadline) {

    }

    @Override
    public void updateGoal(Long goalId, String title, String description, String category, Integer status, Integer priority, String deadline) {

    }

    @Override
    public void deleteGoal(Long goalId) {

    }
}
