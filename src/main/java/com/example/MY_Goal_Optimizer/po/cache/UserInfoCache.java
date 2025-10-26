package com.example.MY_Goal_Optimizer.po.cache;

import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.vo.user.UserVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
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

    private Long id;                      // 用户ID（必填）
    private String account;               // 用户名
    private String userType;              // 角色列表
    private String nickname;              // 昵称
    private String email;                 // 脱敏邮箱（如"al***@example.com"）
    private Byte status;                  // 用户状态（枚举）

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp createdAt;          // 创建时间

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp updatedAt;          // 更新时间

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp lastLoginAt;      // 最后登录时间

    public static class TimestampDeserializer extends JsonDeserializer<Timestamp> {
        @Override
        public Timestamp deserialize(JsonParser p, DeserializationContext context) throws IOException {
            return new Timestamp(p.getLongValue());
        }
    }

    public static UserInfoCache fromUser(User user) {
        UserInfoCache userInfoCache = new UserInfoCache();
        userInfoCache.setId(user.getId());
        userInfoCache.setAccount(user.getAccount());
        userInfoCache.setUserType(user.getUserType().getValue());
        userInfoCache.setNickname(user.getNickname());
        userInfoCache.setEmail(user.getEmail());
        userInfoCache.setStatus(user.getStatus());
        userInfoCache.setCreatedAt(user.getCreatedAt());
        userInfoCache.setUpdatedAt(user.getUpdatedAt());
        userInfoCache.setLastLoginAt(user.getLastLoginAt());
        return userInfoCache;
    }

    public UserVO toVO() {
        UserVO userVO = new UserVO();
        userVO.setId(this.getId());
        userVO.setAccount(this.getAccount());
        userVO.setUserType(this.getUserType());
        userVO.setNickname(this.getNickname());
        userVO.setEmail(this.getEmail());
        userVO.setStatus(this.getStatus());
        userVO.setCreatedAt(this.getCreatedAt());
        userVO.setUpdatedAt(this.getUpdatedAt());
        userVO.setLastLoginAt(this.getLastLoginAt());
        return userVO;
    }

}
