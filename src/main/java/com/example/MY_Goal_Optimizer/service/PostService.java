package com.example.MY_Goal_Optimizer.service;

import com.example.MY_Goal_Optimizer.vo.post.PostSummaryVO;
import com.example.MY_Goal_Optimizer.vo.post.PostVO;

import java.util.List;

public interface PostService {

    //=================GET POST DETAIL=====================

    PostVO getPost(String postId);

    List<PostVO> getPostsByUser(String userId);

    List<PostVO> getPostsByTitle(String keyword);

    //=================GET POST SUMMARY=====================

    PostSummaryVO getPostSummary(String postId);

    List<PostSummaryVO> getPostSummariesByUser(String userId);

    List<PostSummaryVO> getPostSummariesByTitle(String keyword);

    //=================CREATE, UPDATE, DELETE POST==========

    void publishPost(String userId, String goalId, String title, String content);

    void updatePost(String postId, String title, String content);

    void deletePost(String postId);

    //=================POST LIKE=============================

    Boolean updatePostLike(String postId, String userId);

    //=================POST COMMENT==========================



}
