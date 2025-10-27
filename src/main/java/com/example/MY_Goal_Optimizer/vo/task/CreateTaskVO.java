package com.example.MY_Goal_Optimizer.vo.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

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
    private Long goalId;

    @NotBlank(message = "任务内容不能为空")
    @Length(max = 500, message = "任务内容长度不能超过500字")
    private String content;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "日期格式不正确，应为yyyy-MM-ddTHH:mm:ss")
    private String deadline;
}
