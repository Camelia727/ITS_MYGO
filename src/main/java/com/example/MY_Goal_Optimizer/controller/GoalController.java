package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GoalController
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:51
 */
@RestController
@RequestMapping("/api/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;



}
