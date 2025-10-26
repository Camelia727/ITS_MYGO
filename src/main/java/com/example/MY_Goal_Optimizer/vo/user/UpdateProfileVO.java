package com.example.MY_Goal_Optimizer.vo.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * UpdateProfileVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午6:25
 */
@Getter
public class UpdateProfileVO {

    @Length(max = 16, message = "昵称长度不能超过16个字符")
    private String nickName;

    @Email(message = "邮箱格式不正确")
    private String email;

}
