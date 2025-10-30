package com.example.MY_Goal_Optimizer.repository;

import com.example.MY_Goal_Optimizer.po.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByGoalId(String goalId);

    List<Post> findByUserId(String userId);

    List<Post> findByTitleContaining(String keyword);

}
