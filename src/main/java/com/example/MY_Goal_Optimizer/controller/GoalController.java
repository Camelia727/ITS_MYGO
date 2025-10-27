package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.GoalService;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.goal.CreateGoalVO;
import com.example.MY_Goal_Optimizer.vo.goal.GoalVO;
import com.example.MY_Goal_Optimizer.vo.goal.UpdateGoalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/{userId}")
    public ResultVO<List<GoalVO>> getGoals(@PathVariable Long userId) {
        return ResultVO.success(goalService.getGoals(userId), null);
    }

    @GetMapping("/goal/{goalId}")
    public ResultVO<GoalVO> getGoal(@PathVariable Long goalId) {
        return ResultVO.success(goalService.getGoal(goalId), null);
    }

    @PostMapping
    public ResultVO<String> createGoal(@RequestBody CreateGoalVO createGoalVO) {
        goalService.createGoal(createGoalVO.getUserId(), createGoalVO.getTitle(), createGoalVO.getDescription(), createGoalVO.getCategory(), createGoalVO.getPriority(), createGoalVO.getDeadline());
        return ResultVO.success("创建成功", null);
    }

    @PutMapping
    public ResultVO<String> updateGoal(@RequestBody UpdateGoalVO updateGoalVO) {
        goalService.updateGoal(Long.parseLong(updateGoalVO.getId()), updateGoalVO.getTitle(), updateGoalVO.getDescription(), updateGoalVO.getCategory(), updateGoalVO.getStatus(), updateGoalVO.getPriority(), updateGoalVO.getDeadline());
        return ResultVO.success("更新成功", null);
    }

    @DeleteMapping("/{goalId}")
    public ResultVO<String> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResultVO.success("删除成功", null);
    }

}
