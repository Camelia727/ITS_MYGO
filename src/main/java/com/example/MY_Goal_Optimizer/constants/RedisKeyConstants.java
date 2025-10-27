package com.example.MY_Goal_Optimizer.constants;

/**
 * RedisKeyConstants
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午7:36
 */
public final class RedisKeyConstants {

    // USER_INFO
    public static final String USER_INFO_PREFIX = "user_info_";
    public static final long USER_INFO_EXPIRE_TIME = 60 * 60 * 24; // 1 day

    // TODAY_TASKS
    public static final String TODAY_TASKS_PREFIX = "today_tasks_";
    public static final long TODAY_TASKS_EXPIRE_TIME = 60 * 60; // 1 hour

}
