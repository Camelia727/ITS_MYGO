package com.example.MY_Goal_Optimizer.vo.task;

import lombok.Getter;

/**
 * CreateTaskVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:43
 */
@Getter
public class CreateTaskVO {
    Long goalId;
    String content;
    String deadline;
}
