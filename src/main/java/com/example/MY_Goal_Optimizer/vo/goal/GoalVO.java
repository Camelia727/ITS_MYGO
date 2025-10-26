package com.example.MY_Goal_Optimizer.vo.goal;

import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GoalVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:35
 */
@Setter
public class GoalVO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer status; // 1: active, 2: completed, 3: deleted
    private Integer priority;   // 1: high, 2: medium, 3: low
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TaskVO> tasks;
}
