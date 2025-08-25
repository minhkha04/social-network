package com.minhkha.post.controller;

import com.minhkha.post.dto.request.PostRequest;
import com.minhkha.post.dto.response.ApiResponse;
import com.minhkha.post.dto.response.PageResponse;
import com.minhkha.post.dto.response.PostResponse;
import com.minhkha.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<PageResponse<PostResponse>> findByUserId(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .data(postService.findByUserId(page, size))
                .build();
    }
}