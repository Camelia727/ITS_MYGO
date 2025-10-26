package com.example.MY_Goal_Optimizer.vo.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * UserVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:32
 */
@Data
public class UserVO {

    private Long id;
    private String account;
    private String userType = "user"; // 默认普通用户
    private String nickname;
    private String email;
    private Byte status = 1; // 1表示正常
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp lastLoginAt;

}
