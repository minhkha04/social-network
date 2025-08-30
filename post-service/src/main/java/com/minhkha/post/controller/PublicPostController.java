package com.minhkha.post.controller;

import com.minhkha.post.dto.response.ApiResponse;
import com.minhkha.post.dto.response.PageResponse;
import com.minhkha.post.dto.response.PostResponse;
import com.minhkha.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicPostController {

    PostService postService;

    @GetMapping()
    public ApiResponse<PageResponse<PostResponse>> findAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .data(postService.findAll(page, size))
                .build();
    }
}
