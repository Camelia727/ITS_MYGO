package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.exception.AuthException;
import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.po.cache.UserInfoCache;
import com.example.MY_Goal_Optimizer.repository.UserRepository;
import com.example.MY_Goal_Optimizer.service.AuthService;
import com.example.MY_Goal_Optimizer.utils.Argon2Utils;
import com.example.MY_Goal_Optimizer.utils.JwtUtils;
import com.example.MY_Goal_Optimizer.utils.RedisUtils;
import com.example.MY_Goal_Optimizer.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * AuthServiceImpl
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:35
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserVO register(String account, String password, String nickname, String email) {

        if (userRepository.existsByAccount(account)) {
            throw new AuthException(409, "账号已存在");
        }

        if (email != null && !email.trim().isEmpty()
                && userRepository.existsByEmail(email.trim())) {
            throw new AuthException(409, "该邮箱已被注册");
        }

        User user = new User();
        user.setAccount(account);
        user.setPasswordHash(Argon2Utils.encode(password));
        user.setNickname(nickname);
        user.setEmail(email);
        user.setStatus((byte) 1);
        User savedUser = userRepository.save(user);

        return savedUser.toVO();

    }

    @Override
    public String login(String account, String password) {

        User user = userRepository.findByAccount(account).orElse(null);

        // Exceptions
        if (user == null) {
            throw new AuthException(404, "账号不存在");
        }
        if (user.getStatus() == 0) {
            throw new AuthException(403, "账号已被冻结，请联系管理员");
        }

        if (Argon2Utils.matches(password, user.getPasswordHash())) {
            // Login success
            user.setLastLoginAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            cacheUserInfo(UserInfoCache.fromUser(user));
            return JwtUtils.generateToken(user.getId());
        }

        throw new AuthException(401, "密码错误");

    }

    // Redis cache

    /**
     * cacheUserInfo
     *
     * @param userInfoCache 用户信息
     */
    private void cacheUserInfo(UserInfoCache userInfoCache) {
        String USER_CACHE_PREFIX = "user:info:";
        String cacheKey = USER_CACHE_PREFIX + userInfoCache.getId();
        long USER_CACHE_EXPIRE_TIME = 60 * 60;  // 1 hour
        redisUtils.setWithExpire(cacheKey, userInfoCache, USER_CACHE_EXPIRE_TIME);
    }

}
