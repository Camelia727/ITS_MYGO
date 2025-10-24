package com.example.MY_Goal_Optimizer.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * ResultVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:24
 */
@Getter
@Setter
public class ResultVO<T> {
    private Integer code;
    private T data;
    private String message;

    // 构造函数
    public ResultVO(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    // 工厂方法
    public static <T> ResultVO<T> success(T data, String message) {
        return new ResultVO<>(200, data, message);
    }

    public static <T> ResultVO<T> error(Integer code, String message) {
        return new ResultVO<>(code, null, message);
    }
}
