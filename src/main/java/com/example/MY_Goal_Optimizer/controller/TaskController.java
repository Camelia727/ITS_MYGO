package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.TaskService;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.task.CreateTaskVO;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import com.example.MY_Goal_Optimizer.vo.task.UpdateTaskVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/goal/{goal_id}")
    public ResultVO<List<TaskVO>> getTasksByGoalId(@PathVariable Long goal_id) {
        return ResultVO.success(taskService.getTasksByGoalId(goal_id), null);
    }

    @GetMapping("/task/{task_id}")
    public ResultVO<TaskVO> getTaskById(@PathVariable Long task_id) {
        return ResultVO.success(taskService.getTaskById(task_id), null);
    }

    @PostMapping
    public ResultVO<String> createTask(@RequestBody @Valid CreateTaskVO createTaskVO) {
        taskService.createTask(
                createTaskVO.getGoalId(),
                createTaskVO.getContent(),
                createTaskVO.getDeadline()
        );
        return ResultVO.success("创建成功", null);
    }

    @PutMapping
    public ResultVO<String> updateTask(@RequestBody @Valid UpdateTaskVO updateTaskVO) {
        taskService.updateTask(
                updateTaskVO.getId(),
                updateTaskVO.getContent(),
                updateTaskVO.getDeadline()
        );
        return ResultVO.success("更新成功", null);
    }

    @DeleteMapping("/goal/{goal_id}")
    public ResultVO<String> deleteTasksByGoalId(@PathVariable Long goal_id) {
        taskService.deleteTasksByGoalId(goal_id);
        return ResultVO.success("删除成功", null);
    }

    @DeleteMapping("/task/{task_id}")
    public ResultVO<String> deleteTaskById(@PathVariable Long task_id) {
        taskService.deleteTaskById(task_id);
        return ResultVO.success("删除成功", null);
    }

    // 从token获取用户ID（私有方法）
    private Long getUserIdFromToken() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

}
