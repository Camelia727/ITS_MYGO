package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.UserService;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.user.UpdatePasswordVO;
import com.example.MY_Goal_Optimizer.vo.user.UpdateProfileVO;
import com.example.MY_Goal_Optimizer.vo.user.UserVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午6:21
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 获取用户信息接口
    @GetMapping
    public ResultVO<UserVO> getProfile() {
        Long userId = getUserIdFromToken();
        return ResultVO.success(userService.getProfile(userId), null);
    }

    // 更新用户资料接口
    @PutMapping("/profile")
    public ResultVO<Void> updateProfile(@RequestBody @Valid UpdateProfileVO updateProfileVO) {
        Long userId = getUserIdFromToken();
        userService.updateProfile(userId, updateProfileVO.getNickName(), updateProfileVO.getEmail());
        return ResultVO.success(null, "修改成功");
    }

    // 更新用户密码接口
    @PutMapping("/password")
    public ResultVO<Void> updatePassword(@RequestBody @Valid UpdatePasswordVO updatePasswordVO) {
        Long userId = getUserIdFromToken();
        userService.updatePassword(userId, updatePasswordVO.getOldPassword(), updatePasswordVO.getNewPassword());
        return ResultVO.success(null, "修改成功");
    }

    // 从token获取用户ID（私有方法）
    private Long getUserIdFromToken() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

}
