package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.po.Task;
import com.example.MY_Goal_Optimizer.repository.TaskRepository;
import com.example.MY_Goal_Optimizer.service.TodayService;
import com.example.MY_Goal_Optimizer.utils.RedisUtils;
import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.MY_Goal_Optimizer.constants.RedisKeyConstants.TODAY_TASKS_EXPIRE_TIME;
import static com.example.MY_Goal_Optimizer.constants.RedisKeyConstants.TODAY_TASKS_PREFIX;

/**
 * TodayServiceImpl
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/27 下午4:29
 */
@Service
public class TodayServiceImpl implements TodayService {

    private static final Logger log = LoggerFactory.getLogger(TodayServiceImpl.class);
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<TaskVO> getTodayTasks(Long userId) {
        Object tasks_cache = redisUtils.get(TODAY_TASKS_PREFIX + userId);
        if (tasks_cache != null) {
            log.info("redis cache hit");
            return (List<TaskVO>) tasks_cache;
        }

        log.info("redis cache miss");
        List<TaskVO> tasks = taskRepository.findTasksByUserIdAndDeadlineIsToday(userId, LocalDate.now())
                .stream().map(Task::toVO).toList();

        if (tasks != null && !tasks.isEmpty()) {
            redisUtils.setWithExpire(TODAY_TASKS_PREFIX + userId, tasks, TODAY_TASKS_EXPIRE_TIME);
        }

        return tasks;
    }
}
