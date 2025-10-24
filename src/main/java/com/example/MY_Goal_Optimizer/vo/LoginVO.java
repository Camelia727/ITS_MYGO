package com.example.MY_Goal_Optimizer.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * LoginVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:19
 */
@Getter
@Setter
public class LoginVO {

    @NotBlank(message = "账号不能为空")
    @Length(max = 16, message = "账号长度不应多于16位")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "账号只能包含字母和数字")
    String account;

    @NotBlank(message = "密码不能为空")
    @Length(max = 16, message = "密码长度不应多于16位")
    @Pattern(regexp = "^[a-zA-Z0-9!\\-@#$%^&*()_+=,./]*$", message = "密码只能包含字母、数字和特殊字符")
    String password;

}
