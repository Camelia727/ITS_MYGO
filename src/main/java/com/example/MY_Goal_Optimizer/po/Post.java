package com.example.MY_Goal_Optimizer.po;

import com.example.MY_Goal_Optimizer.vo.post.PostVO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.Date;
import java.util.List;

/**
 * Post
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/29 下午4:53
 */
@Document(collection = "posts")
@Data
public class Post {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("goal_id")
    private String goalId;

    private String title;
    private String content;

    private Integer likes;
    private List<Comment> comments;

    @Field("created_at")
    private Date createdAt; // 创建时间

    @Field("updated_at")
    private Date updatedAt; // 更新时间

    /*
     * 评论基类
     *
     * 使用 user reply user 的形式，避免深度嵌套
     */
    @Data
    public static abstract class CommentBase {
        @Field("user_id")
        private String userId;
        @Field("post_id")
        private String postId;  // 关联主帖
        private String content;
        @Field("created_at")
        private Date createdAt; // 创建时间
    }

    // 评论
    public static class Comment extends CommentBase {
        @Field("comment_id")
        private String commentId;
    }

    // 回复
    public static class Reply extends CommentBase {
        @Field("reply_id")
        private String replyId;
        @Field("replied_user_id")
        private String repliedUserId; // 被回复者的 user_id
    }


    // ToVO
    public PostVO toVO() {
        PostVO postVO = new PostVO();
        postVO.setUserId(userId);
        postVO.setGoalId(goalId);
        postVO.setTitle(title);
        postVO.setContent(content);
        postVO.setLikes(likes);
        postVO.setComments(comments);
        postVO.setCreatedAt(createdAt);
        postVO.setUpdatedAt(updatedAt);
        return postVO;
    }

}
