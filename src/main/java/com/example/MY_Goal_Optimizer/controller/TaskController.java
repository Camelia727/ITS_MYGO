package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.TaskService;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.task.CreateTaskVO;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import com.example.MY_Goal_Optimizer.vo.task.UpdateTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TaskController
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:23
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResultVO<List<TaskVO>> getTasksByGoalId(Long goal_id) {
        return ResultVO.success(taskService.getTasksByGoalId(goal_id), null);
    }

    @GetMapping("/task")
    public ResultVO<TaskVO> getTaskById(Long task_id) {
        return ResultVO.success(taskService.getTaskById(task_id), null);
    }

    @PostMapping
    public ResultVO<String> createTask(Long goal_id, CreateTaskVO createTaskVO) {
        return ResultVO.success(taskService.createTask(
                createTaskVO.getGoalId(),
                createTaskVO.getContent(),
                createTaskVO.getDeadline()
        ), null);
    }

    @PutMapping
    public ResultVO<String> updateTask(UpdateTaskVO updateTaskVO) {
        return ResultVO.success(taskService.updateTask(
                updateTaskVO.getId(),
                updateTaskVO.getContent(),
                updateTaskVO.getDeadline()
        ), null);
    }

    @DeleteMapping("/tasks")
    public ResultVO<String> deleteTasksByGoalId(Long goal_id) {
        return ResultVO.success(taskService.deleteTasksByGoalId(goal_id), null);
    }

    @DeleteMapping("/task")
    public ResultVO<String> deleteTaskById(Long task_id) {
        return ResultVO.success(taskService.deleteTaskById(task_id), null);
    }

}
