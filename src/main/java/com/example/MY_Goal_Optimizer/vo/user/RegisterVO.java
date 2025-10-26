package com.example.MY_Goal_Optimizer.vo.user;

import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * RegisterVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:20
 */
@Getter
public class RegisterVO {

    @Size(min = 1, max = 16, message = "账号长度必须在1-16之间")
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "账号仅允许数字、字母、下划线组合")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nickname;

    @Email(message = "邮箱格式不正确")
    private String email;

}
