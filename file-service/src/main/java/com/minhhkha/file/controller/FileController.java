package com.minhhkha.file.controller;

import com.minhhkha.file.dto.response.ApiResponse;
import com.minhhkha.file.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/media")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileController {

    FileService fileService;

    @PostMapping("/upload")
    public ApiResponse<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.builder()
                .data(fileService.uploadFile(file))
                .build();
    }
}
