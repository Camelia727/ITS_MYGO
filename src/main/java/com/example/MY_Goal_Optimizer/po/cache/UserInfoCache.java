package com.example.MY_Goal_Optimizer.po.cache;

import com.example.MY_Goal_Optimizer.po.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * UserInfoCache
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/25 下午7:04
 */
@Setter
@Getter
@NoArgsConstructor
public class UserInfoCache {

    @NotNull(message = "用户ID不能为空")
    private Long id;                      // 用户ID（必填）

    @NotBlank(message = "用户名不能为空")
    private String account;               // 用户名
    private User.UserType userType;       // 角色列表
    private String nickname;              // 昵称

    @Email(message = "邮箱格式不正确")
    private String email;                 // 脱敏邮箱（如"al***@example.com"）

    private Byte status;                  // 用户状态（枚举）

    private Timestamp createdAt;          // 创建时间
    private Timestamp updatedAt;          // 更新时间
    private Timestamp lastLoginTime;      // 最后登录时间

    public static UserInfoCache fromUser(User user) {
        UserInfoCache userInfoCache = new UserInfoCache();
        userInfoCache.setId(user.getId());
        userInfoCache.setAccount(user.getAccount());
        userInfoCache.setUserType(user.getUserType());
        userInfoCache.setNickname(user.getNickname());
        userInfoCache.setEmail(user.getEmail());
        userInfoCache.setStatus(user.getStatus());
        userInfoCache.setCreatedAt(user.getCreatedAt());
        userInfoCache.setUpdatedAt(user.getUpdatedAt());
        userInfoCache.setLastLoginTime(user.getLastLoginAt());
        return userInfoCache;
    }
}
