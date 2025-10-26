package com.example.MY_Goal_Optimizer.vo.goal;

import java.time.LocalDateTime;

/**
 * UpdateGoalVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:56
 */
public class UpdateGoalVO {
    private String id;
    private String title;
    private String description;
    private String category;
    private Integer status;
    private Integer priority;
    private LocalDateTime deadline;
}
