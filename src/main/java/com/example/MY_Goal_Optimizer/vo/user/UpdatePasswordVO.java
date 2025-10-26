package com.example.MY_Goal_Optimizer.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * UpdatePasswordVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午6:24
 */
@Getter
public class UpdatePasswordVO {

    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(max = 16, message = "新密码长度不应多于16位")
    @Pattern(regexp = "^[a-zA-Z0-9!\\-@#$%^&*()_+=,./]*$", message = "密码只能包含字母、数字和特殊字符")
    private String newPassword;

}
