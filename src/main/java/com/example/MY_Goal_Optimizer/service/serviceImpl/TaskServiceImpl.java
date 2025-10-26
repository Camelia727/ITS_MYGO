package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.service.TaskService;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import org.springframework.stereotype.Service;

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


    @Override
    public List<TaskVO> getTasksByGoalId(Long goalId) {
        return List.of();
    }

    @Override
    public String createTask(Long goalId, String content, String deadline) {
        return "";
    }

    @Override
    public TaskVO getTaskById(Long taskId) {
        return null;
    }

    @Override
    public String updateTask(Long taskId, String content, String deadline) {
        return "";
    }

    @Override
    public String deleteTasksByGoalId(Long goalId) {
        return "";
    }

    @Override
    public String deleteTaskById(Long taskId) {
        return "";
    }
}
