package com.example.MY_Goal_Optimizer.po;

import com.example.MY_Goal_Optimizer.po.converter.UserTypeConverter;
import com.example.MY_Goal_Optimizer.vo.user.UserVO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

/**
 * User
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:20
 */
@Entity
@Data
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_account", columnNames = "account"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_users_status", columnList = "status"),
                @Index(name = "idx_users_created", columnList = "created_at")
        })
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "account", nullable = false, columnDefinition = "VARCHAR(32) COLLATE utf8mb4_0900_bin")
    private String account;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "user_type", nullable = false)
    @ColumnDefault("'user'")
    @Convert(converter = UserTypeConverter.class)
    private UserType userType = UserType.USER;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "status", nullable = false)
    @ColumnDefault("1")
    private Byte status = 1;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastLoginAt;


    // ENUMS
    @Getter
    public enum UserType {
        USER("user"),
        ADMIN("admin");

        private final String value;

        UserType(String value) {
            this.value = value;
        }

        public static UserType fromValue(String value) {
            for (UserType type : values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("无效的用户类型: " + value);
        }
    }


    // ToVO
    public UserVO toVO() {
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setAccount(account);
        userVO.setUserType(userType.value);
        userVO.setNickname(nickname);
        userVO.setEmail(email);
        userVO.setStatus(status);
        userVO.setCreatedAt(createdAt);
        userVO.setUpdatedAt(updatedAt);
        userVO.setLastLoginAt(lastLoginAt);
        return userVO;
    }
}
