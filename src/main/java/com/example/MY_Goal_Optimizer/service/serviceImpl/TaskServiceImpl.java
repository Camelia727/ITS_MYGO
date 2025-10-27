package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.exception.RunException;
import com.example.MY_Goal_Optimizer.po.Goal;
import com.example.MY_Goal_Optimizer.po.Task;
import com.example.MY_Goal_Optimizer.repository.GoalRepository;
import com.example.MY_Goal_Optimizer.repository.TaskRepository;
import com.example.MY_Goal_Optimizer.service.TaskService;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TaskServiceImpl
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:24
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskVO> getTasksByGoalId(Long goalId) {
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new RunException(400, "目标不存在或已被删除");
        }
        return goal.getTasks().stream().map(Task::toVO).toList();
    }

    @Override
    public void createTask(Long goalId, String content, String deadline) {
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new RunException(400, "目标不存在或已被删除");
        }

        Task task = new Task();
        task.setGoal(goal);
        task.setContent(content);
        task.setDeadline(LocalDateTime.parse(deadline));
        taskRepository.save(task);

        goal.getTasks().add(task);
        goalRepository.save(goal);
    }

    @Override
    public TaskVO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new RunException(400, "任务不存在或已被删除");
        }
        return task.toVO();
    }

    @Override
    public void updateTask(Long taskId, String content, String deadline) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new RunException(400, "任务不存在或已被删除");
        }
        if (content.isEmpty() && deadline.trim().isEmpty()) {
            throw new RunException(400, "任务内容和截止日期都为空");
        }
        if (!content.isEmpty()) {
            task.setContent(content);
        }
        if (!deadline.trim().isEmpty()) {
            task.setDeadline(LocalDateTime.parse(deadline));
        }
        taskRepository.save(task);
    }

    @Override
    public void deleteTasksByGoalId(Long goalId) {
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new RunException(400, "目标不存在或已被删除");
        }
        List<Task> tasks = taskRepository.findAllByGoalId(goalId);
        for (Task task : tasks) {
            taskRepository.delete(task);
        }
    }

    @Override
    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new RunException(400, "任务不存在或已被删除");
        }
        taskRepository.delete(task);
    }
}
