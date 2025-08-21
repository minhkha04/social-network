package com.minhkha.identity.helper;

import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TemplateRender {

    ResourceLoader resourceLoader;

    String BASE_TEMPLATE_PATH = "classpath:templates/mail/";

    private String loadTemplate(String templateName) {
        try {
            log.info("Loading template: {}", BASE_TEMPLATE_PATH + templateName);
            Resource resource = resourceLoader.getResource(BASE_TEMPLATE_PATH + templateName);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.TEMPLATE_NOT_FOUND);
        }
    }

    public String render(String templateName, Map<String, String> values) {
        String content = loadTemplate(templateName);

        for (Map.Entry<String, String> entry : values.entrySet()) {
            content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return content;
    }
}
