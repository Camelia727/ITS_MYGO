package com.example.MY_Goal_Optimizer.exception;

import lombok.Getter;

/**
 * TokenException
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:22
 */
@Getter
public class TokenException extends RuntimeException {

    public final int code;

    public TokenException(int code, String message) {
        super(message);
        this.code = code;
    }

}
