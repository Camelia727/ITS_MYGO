package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.UserVO;

public interface AuthService {

    UserVO register(String account, String password, String nickname, String email);

    String login(String account, String password);

}
