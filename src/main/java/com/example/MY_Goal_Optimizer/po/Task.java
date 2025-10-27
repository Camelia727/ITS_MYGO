package com.example.MY_Goal_Optimizer.po;

import com.example.MY_Goal_Optimizer.vo.task.TaskVO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Task
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:23
 */
@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    @JsonManagedReference
    private Goal goal;

    @Column(nullable = false, length = 200)
    private String content;

    @Column(nullable = false)
    private Boolean completed = false;

    private LocalDateTime deadline;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TaskVO toVO() {
        TaskVO taskVO = new TaskVO();
        taskVO.setId(this.id);
        taskVO.setContent(this.content);
        taskVO.setCompleted(this.completed);
        taskVO.setDeadline(this.deadline);
        taskVO.setCreatedAt(this.createdAt);
        taskVO.setUpdatedAt(this.updatedAt);
        return taskVO;
    }

}
