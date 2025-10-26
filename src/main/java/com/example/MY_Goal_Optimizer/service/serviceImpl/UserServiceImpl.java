package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.exception.AuthException;
import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.po.cache.UserInfoCache;
import com.example.MY_Goal_Optimizer.repository.UserRepository;
import com.example.MY_Goal_Optimizer.service.UserService;
import com.example.MY_Goal_Optimizer.utils.Argon2Utils;
import com.example.MY_Goal_Optimizer.utils.RedisUtils;
import com.example.MY_Goal_Optimizer.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

import static com.example.MY_Goal_Optimizer.constants.RedisKeyConstants.USER_INFO_PREFIX;

/**
 * UserServiceImpl
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午6:22
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserVO getProfile(Long userId) {

        UserInfoCache userInfoCache = (UserInfoCache) redisUtils.get(USER_INFO_PREFIX + userId);
        if (userInfoCache != null) {
            return userInfoCache.toVO();
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            redisUtils.setWithExpire(USER_INFO_PREFIX + userId, UserInfoCache.fromUser(user), 60 * 60);
            return user.toVO();
        }
        throw new AuthException(401, "用户不存在或已注销");

    }

    @Override
    public void updateProfile(Long userId, String nickname, String email) {

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (!StringUtils.hasText(nickname) && !StringUtils.hasText(email)) {
                throw new AuthException(400, "昵称和邮箱均为空，请至少输入一个");
            }
            if (StringUtils.hasText(nickname)) {
                user.setNickname(nickname);
            }
            if (StringUtils.hasText(email)) {
                user.setEmail(email);
            }
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            redisUtils.del(USER_INFO_PREFIX + userId);
            return;
        }
        throw new AuthException(401, "用户不存在或已注销");

    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (!Argon2Utils.matches(oldPassword, user.getPasswordHash())) {
                throw new AuthException(401, "旧密码错误");
            }
            if (Argon2Utils.matches(newPassword, user.getPasswordHash())) {
                throw new AuthException(400, "新密码不可与旧密码相同");
            }
            user.setPasswordHash(Argon2Utils.encode(newPassword));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            redisUtils.del(USER_INFO_PREFIX + userId);
            return;
        }
        throw new AuthException(401, "用户不存在或已注销");

    }

}
