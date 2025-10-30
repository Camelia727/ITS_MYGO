package com.example.MY_Goal_Optimizer.service.serviceImpl;

import com.example.MY_Goal_Optimizer.exception.RunException;
import com.example.MY_Goal_Optimizer.po.Goal;
import com.example.MY_Goal_Optimizer.po.Post;
import com.example.MY_Goal_Optimizer.po.User;
import com.example.MY_Goal_Optimizer.repository.GoalRepository;
import com.example.MY_Goal_Optimizer.repository.PostRepository;
import com.example.MY_Goal_Optimizer.repository.UserRepository;
import com.example.MY_Goal_Optimizer.service.PostService;
import com.example.MY_Goal_Optimizer.utils.RedisUtils;
import com.example.MY_Goal_Optimizer.vo.post.PostSummaryVO;
import com.example.MY_Goal_Optimizer.vo.post.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.example.MY_Goal_Optimizer.constants.RedisKeyConstants.POST_LIKE_PREFIX;
import static com.example.MY_Goal_Optimizer.constants.RedisKeyConstants.POST_USER_LIKE_PREFIX;

/**
 * PostServiceImpl
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/29 下午10:13
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private RedisUtils redisUtils;

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostVO getPost(String postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new RunException(404, "帖子不存在或已被删除");
        }

        return post.toVO();
    }

    @Override
    public List<PostVO> getPostsByUser(String userId) {
        if (!userRepository.existsById(Long.parseLong(userId))) {
            throw new RunException(404, "用户不存在或已被删除");
        }

        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(Post::toVO).toList();
    }

    @Override
    public List<PostVO> getPostsByTitle(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            throw new RunException(400, "搜索关键字不能为空");
        }

        List<Post> posts = postRepository.findByTitleContaining(keyword);
        return posts.stream().map(Post::toVO).toList();
    }

    @Override
    public PostSummaryVO getPostSummary(String postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new RunException(404, "帖子不存在或已被删除");
        }

        return toSummaryVO(post);
    }

    @Override
    public List<PostSummaryVO> getPostSummariesByUser(String userId) {
        if (!userRepository.existsById(Long.parseLong(userId))) {
            throw new RunException(404, "用户不存在或已被删除");
        }

        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(this::toSummaryVO).toList();
    }

    @Override
    public List<PostSummaryVO> getPostSummariesByTitle(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            throw new RunException(400, "搜索关键字不能为空");
        }

        List<Post> posts = postRepository.findByTitleContaining(keyword);
        return posts.stream().map(this::toSummaryVO).toList();
    }

    @Override
    public void publishPost(String userId, String goalId, String title, String content) {
        if (!userRepository.existsById(Long.parseLong(userId))) {
            throw new RunException(404, "用户不存在或已被删除");
        }

        Goal goal = goalRepository.findById(Long.parseLong(goalId)).orElse(null);
        if (goal == null) {
            throw new RunException(404, "目标不存在或已被删除");
        }
        if (goal.getUser().getId() != Long.parseLong(userId)) {
            throw new RunException(403, "非个人目标，无权发布帖子");
        }

        Post post = new Post();
        post.setUserId(userId);
        post.setGoalId(goalId);
        post.setTitle(title);
        post.setContent(content);
        post.setLikes(0);
        post.setComments(List.of());
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());
        postRepository.save(post);
    }

    @Override
    public void updatePost(String postId, String title, String content) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new RunException(404, "帖子不存在或已被删除");
        }

        if (title != null && !title.isEmpty()) {
            post.setTitle(title);
        }
        if (content != null && !content.isEmpty()) {
            post.setContent(content);
        }

        post.setUpdatedAt(new Date());
        postRepository.save(post);
    }

    @Override
    public void deletePost(String postId) {
         postRepository.deleteById(postId);
    }

    @Override
    public Boolean updatePostLike(String postId, String userId) {
        if (!postRepository.existsById(postId)) {
            throw new RunException(404, "帖子不存在或已被删除");
        }

        System.out.println("userId: " + userId);

        String userLikeKey = POST_USER_LIKE_PREFIX + postId;
        String postLikeKey = POST_LIKE_PREFIX + postId;
        Boolean isLiked = redisUtils.isMember(userLikeKey, userId);
        if (Boolean.TRUE.equals(isLiked)) {
            // 该用户已经点赞，需要取消点赞
            redisUtils.removeSet(userLikeKey, userId);
            redisUtils.increment(postLikeKey, -1L);
        }
        else {
            // 该用户没有点赞，需要点赞
            redisUtils.addSet(userLikeKey, userId);
            redisUtils.increment(postLikeKey, 1L);
        }
        // 同步点赞数到mongo
        syncLikeToMongo(postId);
        return isLiked;
    }

    @Async
    private void syncLikeToMongo(String postId) {
        try {
            String likeKey = POST_LIKE_PREFIX + postId;
            Integer likes = (Integer) redisUtils.get(likeKey);

            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) {
                throw new RunException(404, "帖子不存在或已被删除");
            }

            post.setLikes(likes);
            postRepository.save(post);
        } catch (Exception e) {
            log.error("同步点赞数到mongo失败", e);
            throw new RunException(400, "同步点赞数到mongo失败");
        }
    }

    private PostSummaryVO toSummaryVO(Post post) {
        Long userId = Long.valueOf(post.getUserId());
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RunException(404, "用户不存在或已被删除");
        }

        Long goalId = Long.valueOf(post.getGoalId());
        Goal goal = goalRepository.findById(goalId).orElse(null);
        if (goal == null) {
            throw new RunException(404, "目标不存在或已被删除");
        }

        return getPostSummaryVO(post, user, goal);
    }

    private static PostSummaryVO getPostSummaryVO(Post post, User user, Goal goal) {
        PostSummaryVO summaryVO = new PostSummaryVO();
        summaryVO.setId(post.getId());
        summaryVO.setUserNickname(user.getNickname());
        summaryVO.setGoalTitle(goal.getTitle());
        summaryVO.setGoalDescription(goal.getDescription());
        summaryVO.setTitle(post.getTitle());
        summaryVO.setContent(post.getContent());
        summaryVO.setLikes(post.getLikes());
        summaryVO.setComments(post.getComments().size());
        summaryVO.setCreatedAt(post.getCreatedAt());
        summaryVO.setUpdatedAt(post.getUpdatedAt());
        return summaryVO;
    }
}
