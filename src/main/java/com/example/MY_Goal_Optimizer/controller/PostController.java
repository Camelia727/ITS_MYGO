package com.example.MY_Goal_Optimizer.controller;

import com.example.MY_Goal_Optimizer.service.PostService;
import com.example.MY_Goal_Optimizer.vo.ResultVO;
import com.example.MY_Goal_Optimizer.vo.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PostController
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/29 下午10:12
 */
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public ResultVO<PostVO> getPost(@PathVariable String postId) {
        return ResultVO.success(postService.getPost(postId), null);
    }

    @GetMapping("/user/{userId}")
    public ResultVO<List<PostVO>> getPostsByUser(@PathVariable String userId) {
        return ResultVO.success(postService.getPostsByUser(userId), null);
    }

    @GetMapping("/title/{keyword}")
    public ResultVO<List<PostVO>> getPostsByTitle(@PathVariable String keyword) {
        return ResultVO.success(postService.getPostsByTitle(keyword), null);
    }

    @GetMapping("/summary/{postId}")
    public ResultVO<PostSummaryVO> getPostSummary(@PathVariable String postId) {
        return ResultVO.success(postService.getPostSummary(postId), null);
    }

    @GetMapping("/summary/user/{userId}")
    public ResultVO<List<PostSummaryVO>> getPostSummariesByUser(@PathVariable String userId) {
        return ResultVO.success(postService.getPostSummariesByUser(userId), null);
    }

    @GetMapping("/summary/title/{keyword}")
    public ResultVO<List<PostSummaryVO>> getPostSummariesByTitle(@PathVariable String keyword) {
        return ResultVO.success(postService.getPostSummariesByTitle(keyword), null);
    }

    @PostMapping
    public ResultVO<String> publishPost(@RequestBody PublishPostVO publishPostVO) {
        postService.publishPost(publishPostVO.getUserId(), publishPostVO.getGoalId(), publishPostVO.getTitle(), publishPostVO.getContent());
        return ResultVO.success("发布成功", null);
    }

    @PutMapping
    public ResultVO<String> updatePost(@RequestBody UpdatePostVO updatePostVO) {
         postService.updatePost(updatePostVO.getPostId(), updatePostVO.getTitle(), updatePostVO.getContent());
         return ResultVO.success("更新成功", null);
    }

    @DeleteMapping("/{postId}")
    public ResultVO<String> deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
        return ResultVO.success("删除成功", null);
    }

    @PutMapping("like/{postId}")
    public ResultVO<String> updatePostLike(@PathVariable String postId) {
        String userId = getUserIdFromToken().toString();
        String data = postService.updatePostLike(postId, userId) ? "取消点赞成功" : "点赞成功";
        return ResultVO.success(data, null);
    }

    @PutMapping("comment/add/{postId}")
    public ResultVO<String> addComment(@PathVariable String postId, @RequestBody AddCommentVO addCommentVO) {
        return ResultVO.success("评论成功", null);
    }

    @PutMapping("comment/reply/{commentId}")
    public ResultVO<String> addReply(@PathVariable String commentId, @RequestBody AddCommentVO addCommentVO) {
        return ResultVO.success("回复成功", null);
    }

    @PutMapping("reply/reply/{replyId}")
    public ResultVO<String> addReplyToReply(@PathVariable String replyId, @RequestBody AddCommentVO addCommentVO) {
        return ResultVO.success("回复成功", null);
    }

    @DeleteMapping("comment/{commentId}")
    public ResultVO<String> deleteComment(@PathVariable String commentId) {
        return ResultVO.success("删除成功", null);
    }


    // 从token获取用户ID（私有方法）
    private Long getUserIdFromToken() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
