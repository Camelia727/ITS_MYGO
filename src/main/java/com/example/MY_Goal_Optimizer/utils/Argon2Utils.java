package com.example.MY_Goal_Optimizer.utils;

import com.example.MY_Goal_Optimizer.exception.AuthException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Argon2Utils
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:37
 */
public class Argon2Utils {

    private static final int ITERATIONS = 2;
    private static final int MEMORY_SIZE = 1024 * 1024;
    private static final int PARALLELISM = 1;

    private static final Argon2 argon2 = Argon2Factory.create();

    public static String encode(CharSequence rawPassword) {
        try {
            return argon2.hash(ITERATIONS, MEMORY_SIZE, PARALLELISM, rawPassword.toString().toCharArray());
        } catch (Exception e) {
            throw new AuthException(500, "密码加密失败");
        }
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return argon2.verify(encodedPassword, rawPassword.toString().toCharArray());
        } catch (Exception e) {
            throw new AuthException(401, "密码验证失败");
        }
    }

    public void destroy() {
        argon2.wipeArray(new byte[0]);
    }

}
