package com.example.MY_Goal_Optimizer.vo.goal;

import lombok.Getter;

/**
 * CreateGoalVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:55
 */
@Getter
public class CreateGoalVO {
    private Long userId;
    private String title;
    private String description;
    private String category;
    private Integer priority;
    private String deadline;
}
