package com.example.MY_Goal_Optimizer.vo.goal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

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

    @NotBlank(message = "标题不能为空")
    @Length(max = 16, message = "标题长度不能超过16个字符")
    private String title;

    @Length(max = 128, message = "描述长度不能超过128个字符")
    private String description;
    private String category;
    private Integer priority;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "日期格式不正确，应为yyyy-MM-ddTHH:mm:ss")
    private String deadline;
}
