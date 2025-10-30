package com.example.MY_Goal_Optimizer.vo.post;

import com.example.MY_Goal_Optimizer.po.Post;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * PostVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/29 下午10:16
 */
@Data
public class PostVO {
    private String userId;
    private String goalId;
    private String title;
    private String content;
    private Integer likes;
    private List<Post.Comment> comments;
    private Date createdAt;
    private Date updatedAt;
}
