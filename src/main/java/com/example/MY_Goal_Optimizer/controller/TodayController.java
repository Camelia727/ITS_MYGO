package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.GoalService;
import com.example.MY_Goal_Optimizer.service.TaskService;
import com.example.MY_Goal_Optimizer.service.TodayService;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.goal.GoalVO;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TodayController
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/27 下午4:27
 */
@RestController
@RequestMapping("/api/today")
public class TodayController {

    @Autowired
    private TodayService todayService;

    @GetMapping("/tasks")
    public ResultVO<List<TaskVO>> getTodayTasks() {
        return ResultVO.success(todayService.getTodayTasks(getUserIdFromToken()), null);
    }

    // 从token获取用户ID（私有方法）
    private Long getUserIdFromToken() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

}
