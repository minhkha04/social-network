package com.minhhkha.file.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileService {

    public Object uploadFile(MultipartFile file) throws IOException {
        Path folder = Paths.get("E:/upload");
        String fileNameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());


        String fileName = Objects.isNull(fileNameExtension)
                ? UUID.randomUUID().toString()
                : UUID.randomUUID() + "." + fileNameExtension;

        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return null;
    }
}
