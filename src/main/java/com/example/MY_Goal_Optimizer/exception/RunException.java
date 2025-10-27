package com.example.MY_Goal_Optimizer.exception;

import lombok.Getter;

/**
 * RunException
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/27 上午9:35
 */
@Getter
public class RunException extends RuntimeException {

    private final int code;

    public RunException(int code, String message) {
        super(message);
        this.code = code;
    }

}
