package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.task.TaskVO;

import java.util.List;

public interface TaskService {

    List<TaskVO> getTasksByGoalId(Long goalId);

    void createTask(Long goalId, String content, String deadline);

    TaskVO getTaskById(Long taskId);

    void updateTask(Long taskId, String content, String deadline);

    void deleteTasksByGoalId(Long goalId);

    void deleteTaskById(Long taskId);

}
