package com.example.MY_Goal_Optimizer.exception;

import com.example.MY_Goal_Optimizer.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResultVO<?> handleRuntimeException(RuntimeException e) {
        log.error("服务器错误，请稍后再试", e);
        return ResultVO.error(500, "服务器错误，请稍后再试");
    }

    // Valid数据校验不通过
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResultVO.error(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    // Token异常
    @ExceptionHandler(TokenException.class)
    public ResultVO<?> handleTokenException(TokenException e) {
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    // 用户注册、登录异常
    @ExceptionHandler(AuthException.class)
    public ResultVO<?> handleAuthException(AuthException e) {
        return ResultVO.error(e.getCode(), e.getMessage());
    }

//    // 内容异常
//    @ExceptionHandler(ItemException.class)
//    public ResultVO<?> handleItemException(ItemException e) {
//        return ResultVO.error(e.getCode(), e.getMessage());
//    }

}
