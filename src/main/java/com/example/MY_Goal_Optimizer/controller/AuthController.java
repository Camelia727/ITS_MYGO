package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.AuthService;
import com.example.MY_Goal_Optimizer.vo.LoginVO;
import com.example.MY_Goal_Optimizer.vo.RegisterVO;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:20
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 注册接口
    @PostMapping("/register")
    public ResultVO<UserVO> register(@RequestBody @Valid RegisterVO registerVO) {
        return ResultVO.success(authService.register(
                        registerVO.getAccount(), registerVO.getPassword(),
                        registerVO.getNickname(), registerVO.getEmail()),
                "注册成功");
    }

    // 登录接口
    @PostMapping("/login")
    public ResultVO<String> login(@RequestBody @Valid LoginVO loginVO) {
        return ResultVO.success(authService.login(loginVO.getAccount(),
                        loginVO.getPassword()),
                "登录成功");
    }

}
