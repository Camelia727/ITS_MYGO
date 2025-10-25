package com.example.MY_Goal_Optimizer.utils;

import jakarta.annotation.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/25 上午11:20
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private static final String UNLOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 陈工失败
     */
    public boolean expire(String key, long time) {
        if (time <= 0) {
            return false;
        }
        try {
            Boolean result = redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("expire key:{},time:{} error", key, time, e);
            return false;
        }
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean result = redisTemplate.hasKey(key);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("hasKey key:{} error", key, e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    public boolean del(String... keys) {
        if (keys == null || keys.length == 0) {
            return false;
        }
        try {
            if (keys.length == 1) {
                return Boolean.TRUE.equals(redisTemplate.delete(keys[0]));
            } else {
                Collection<String> keyCollection = Arrays.asList(keys);
                Long result = redisTemplate.delete(keyCollection);
                return result != null && result > 0;
            }
        } catch (Exception e){
            log.error("del key:{} error", keys, e);
            return false;
        }
    }

    /**
     * 根据前缀删除key
     * @param prefix 前缀
     */
    public boolean deleteByPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            return false;
        }

        try {
            // TODO: keys() => execute lua script [SCAN] to avoid blocking
            Set<String> keys = redisTemplate.keys(prefix + "*");
            Long result =redisTemplate.delete(keys);
            return result != null && result > 0;
        }catch (Exception e){
            log.error("deleteByPrefix prefix:{} error", prefix, e);
            return false;
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @Nullable
    public Object get(String key) {
        if (key == null || key.trim().isEmpty()) {
            return null;
        }

        try{
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e){
            log.error("get key:{} error", key, e);
            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        if (key == null || key.isEmpty() || value == null) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set key:{},value:{} error", key, value, e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0
     * @return true成功 false 失败
     */
    public boolean setWithExpire(String key, Object value, long time) {
        if (key == null || key.isEmpty() || value == null || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("setWithExpire key:{},value:{},time:{} error", key, value, time, e);
            return false;
        }
    }


    /**
     * 根据参数查询相关KEY集合
     *
     * @param pattern 匹配模式(如 "user:*")
     * @return 结果
     */
    public Set<String> getKeyListByPattern(String pattern){
        if (pattern == null || pattern.trim().isEmpty()) {
            return Collections.emptySet();
        }

        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            log.error("getKeyListByPattern pattern:{} error", pattern, e);
            return Collections.emptySet();
        }
    }

    //============================Lock=============================

    /**
     * 获得锁
     *
     * @param key           锁的键
     * @param clientId      锁的值(通常使用唯一标识)
     * @param releaseTime   锁过期时间 秒
     * @return 结果
     */
    public boolean lock(String key, String clientId, long releaseTime) {
        if(key == null || key.isEmpty() || clientId.trim().isEmpty() || releaseTime <= 0) {
            return false;
        }

        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, clientId, releaseTime, TimeUnit.SECONDS);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("lock key:{},value:{},releaseTime:{} error", key, clientId, releaseTime, e);
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param key      锁的键
     * @param clientId 锁的值（防止删除其他线程的锁）
     */
    public boolean unLock(String key, String clientId) {
        if (key == null || key.isEmpty() || clientId == null) {
            return false;
        }

        try {
            Long result = redisTemplate.execute(
                    new DefaultRedisScript<>(UNLOCK_LUA_SCRIPT, Long.class),
                    Collections.singletonList(key),
                    clientId
            );
            return result != null && result == 1;
        } catch (Exception e) {
            log.error("unLock key:{} error", key, e);
            return false;
        }
    }

    //============================List=============================

    /**
     * 向指定list的队列右侧批量添加value
     *
     * @param key key
     */
    public boolean rightPush(String key, String value) {
        if (key == null || key.isEmpty() || value == null) {
            return false;
        }

        try {
            Long result = redisTemplate.opsForList().rightPush(key, value);
            return result != null && result > 0;
        } catch (Exception e) {
            log.error("rightPush key:{},value:{} error", key, value, e);
            return false;
        }

    }

    /**
     * 向指定list的队列左侧批量添加value
     *
     * @param key key
     */
    public boolean leftPush(String key, String value) {
        if (key == null || key.isEmpty() || value == null) {
            return false;
        }

        try {
            Long result = redisTemplate.opsForList().leftPush(key, value);
            return result != null && result > 0;
        } catch (Exception e) {
            log.error("leftPush key:{},value:{} error", key, value, e);
            return false;
        }

    }


    /**
     * 移除并获取指定list中(队列-头部/尾部)第一个元素
     *
     * @param key key
     */
    @Nullable
    public Object leftPop(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }

        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("leftPop key:{} error", key, e);
            return null;
        }

    }

    //============================Increment===================

    /**
     * 指定key+！
     *
     * @param key key
     */
    @Nullable
    public Long increment(String key,Long num) {
        if (key == null || key.isEmpty() || num == null) {
            return null;
        }

        try {
            return redisTemplate.opsForValue().increment(key,num);
        } catch (Exception e) {
            log.error("increment key:{},num:{} error", key, num, e);
            return null;
        }
    }

}
