package com.example.MY_Goal_Optimizer.vo.task;

import com.example.MY_Goal_Optimizer.po.Goal;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TaskVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:25
 */
@Setter
public class TaskVO {
    private Integer id;
    private String content;
    private Boolean completed;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
