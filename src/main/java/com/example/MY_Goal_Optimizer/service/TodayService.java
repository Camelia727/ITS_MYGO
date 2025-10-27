package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.task.TaskVO;

import java.util.List;

/**
 * TodayService
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/27 下午4:29
 */
public interface TodayService {

    List<TaskVO> getTodayTasks(Long userId);

}
