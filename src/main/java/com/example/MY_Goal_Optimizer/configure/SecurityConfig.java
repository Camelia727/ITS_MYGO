package com.example.MY_Goal_Optimizer.configure;

import com.example.MY_Goal_Optimizer.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:20
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // 放行 /api/** 下的所有接口
                        .requestMatchers("/actuator/health").permitAll() // 放行健康检查端点
                        .anyRequest().authenticated()            // 其他路径需要认证
                )
                .formLogin(form -> form
                        .loginPage("/login")                    // 自定义登录页（可选）
                        .defaultSuccessUrl("/", true)           // 登录成功跳转路径
                )
                .csrf(AbstractHttpConfigurer::disable)              // 禁用 CSRF（仅测试环境）
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);    // 添加 JWT 过滤器
        return http.build();
    }

    @Bean
    @Profile("test")
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()                    // 测试环境放行所有请求
                )
                .csrf(AbstractHttpConfigurer::disable)              // 禁用 CSRF
                .formLogin(AbstractHttpConfigurer::disable)         // 禁用表单登录
                .httpBasic(AbstractHttpConfigurer::disable);        // 禁用HTTP基本认证
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
