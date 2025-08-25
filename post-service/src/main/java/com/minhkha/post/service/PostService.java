package com.minhkha.post.service;

import com.minhkha.post.dto.request.PostRequest;
import com.minhkha.post.dto.response.PostResponse;
import com.minhkha.post.entity.Post;
import com.minhkha.post.mapper.PostMapper;
import com.minhkha.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostService {

    PostRepository postRepository;
    PostMapper postMapper;

    public PostResponse createPost(PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = Post.builder()
                .content(request.getContent())
                .createdAt(Instant.now())
                .modifiedAt(Instant.now())
                .userId(authentication.getName())
                .build();

        post = postRepository.save(post);
        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> findByUserId() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(postMapper::toPostResponse).toList();
    }
}
