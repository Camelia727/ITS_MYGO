package com.example.MY_Goal_Optimizer.exception;

import lombok.Getter;

/**
 * AuthException
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:36
 */
@Getter
public class AuthException extends RuntimeException {

    private final int code;

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
    }

}
