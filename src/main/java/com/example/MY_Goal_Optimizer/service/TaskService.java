package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.task.TaskVO;

import java.util.List;

public interface TaskService {

    List<TaskVO> getTasksByGoalId(Long goalId);

    String createTask(Long goalId, String content, String deadline);

    TaskVO getTaskById(Long taskId);

    String updateTask(Long taskId, String content, String deadline);

    String deleteTasksByGoalId(Long goalId);

    String deleteTaskById(Long taskId);

}
