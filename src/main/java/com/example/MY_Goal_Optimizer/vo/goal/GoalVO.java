package com.example.MY_Goal_Optimizer.vo.goal;

import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
@Data
public class GoalVO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer status; // 1: active, 2: completed, 3: deleted
    private Integer priority;   // 1: high, 2: medium, 3: low

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime deadline;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    private List<TaskVO> tasks;
}
