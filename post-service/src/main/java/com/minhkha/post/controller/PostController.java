package com.minhkha.post.controller;

import com.minhkha.post.dto.request.PostRequest;
import com.minhkha.post.dto.response.ApiResponse;
import com.minhkha.post.dto.response.PostResponse;
import com.minhkha.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostController {

    PostService postService;

    @PostMapping("/create")
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
                .data(postService.createPost(request))
                .build();
    }

    @GetMapping("/my-post")
    public ApiResponse<List<PostResponse>> findByUserId() {
        return ApiResponse.<List<PostResponse>>builder()
                .data(postService.findByUserId())
                .build();
    }
}