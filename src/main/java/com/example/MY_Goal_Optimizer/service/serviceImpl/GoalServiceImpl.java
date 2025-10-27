package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.exception.AuthException;
import com.example.MY_Goal_Optimizer.exception.RunException;
import com.example.MY_Goal_Optimizer.po.Goal;
import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.repository.GoalRepository;
import com.example.MY_Goal_Optimizer.repository.UserRepository;
import com.example.MY_Goal_Optimizer.service.GoalService;
import com.example.MY_Goal_Optimizer.vo.goal.GoalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GoalVO> getGoals(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AuthException(401, "用户不存在[id]");
        }

        return goalRepository.findByUserId(userId).stream().map(Goal::toVO).toList();
    }

    @Override
    public GoalVO getGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new AuthException(404, "目标不存在[id]");
        }

        return goal.toVO();
    }

    @Override
    public void createGoal(Long userId, String title, String description, String category, Integer priority, String deadline) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RunException(400, "用户不存在[id]");
        }

        Goal goal = new Goal();
        goal.setUser(user);
        goal.setTitle(title);
        goal.setDescription(description);
        goal.setCategory(category);
        goal.setPriority(priority);
        goal.setDeadline(LocalDateTime.parse(deadline));
        goalRepository.save(goal);
    }

    @Override
    public void updateGoal(Long goalId, String title, String description, String category, Integer status, Integer priority, String deadline) {
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new AuthException(404, "目标不存在[id]");
        }

        if (title != null && !title.isEmpty()) {
            goal.setTitle(title);
        }
        if (description != null && !description.isEmpty()) {
            goal.setDescription(description);
        }
        if (category != null && !category.isEmpty()) {
            goal.setCategory(category);
        }
        if (status!= null) {
            goal.setStatus(status);
        }
        if (priority!= null) {
            goal.setPriority(priority);
        }
        if (deadline != null && !deadline.isEmpty()) {
            goal.setDeadline(LocalDateTime.parse(deadline));
        }
        goalRepository.save(goal);
    }

    @Override
    public void updateGoalStatus(Long goalId, Integer status) {
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new RunException(404, "目标不存在[id]");
        }
        if (Objects.equals(status, goal.getStatus())) {
            throw new RunException(400, "请设置不同的状态");
        }

        goal.setStatus(status);
        goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(Long goalId) {
         goalRepository.deleteById(goalId);
    }
}
