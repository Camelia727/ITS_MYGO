package com.example.MY_Goal_Optimizer.vo.post;

import lombok.Data;

import java.util.Date;

/**
 * PostSummaryVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/30 上午10:26
 */
@Data
public class PostSummaryVO {
    private String id;
    private String userNickname;
    private String goalTitle;
    private String goalDescription;
    private String title;
    private String content;
    private Integer likes;
    private Integer comments;
    private Date createdAt;
    private Date updatedAt;
}
