package com.example.MY_Goal_Optimizer.po;

import com.example.MY_Goal_Optimizer.vo.goal.GoalVO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Goal
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/26 下午11:27
 */
@Entity
@Data
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String category;

    private Integer status; // 1: active, 2: completed, 3: deleted

    private Integer priority;   // 1: high, 2: medium, 3: low

    private LocalDateTime deadline;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "goal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks;

    public GoalVO toVO() {
        GoalVO goalVO = new GoalVO();
        goalVO.setId(id);
        goalVO.setTitle(title);
        goalVO.setDescription(description);
        goalVO.setCategory(category);
        goalVO.setStatus(status);
        goalVO.setPriority(priority);
        goalVO.setDeadline(deadline);
        goalVO.setCreatedAt(createdAt);
        goalVO.setUpdatedAt(updatedAt);
        return goalVO;
    }

}
