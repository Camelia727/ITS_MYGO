package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.user.UserVO;

public interface UserService {

    UserVO getProfile(Long userId);

    void updateProfile(Long userId, String nickname, String email);

    void updatePassword(Long userId, String oldPassword, String newPassword);

}
