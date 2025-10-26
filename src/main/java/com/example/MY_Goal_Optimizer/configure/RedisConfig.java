package com.example.MY_Goal_Optimizer.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/25 上午11:06
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用 GenericJackson2JsonRedisSerializer 序列化值
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        template.setKeySerializer(new StringRedisSerializer()); // 键使用 String 序列化
        template.setValueSerializer(serializer); // 值使用 JSON 序列化
        template.setHashKeySerializer(new StringRedisSerializer()); // Hash 键使用 String
        template.setHashValueSerializer(serializer); // Hash 值使用 JSON

        return template;
    }

}
