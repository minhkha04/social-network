package com.minhkha.post.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse <T> {

    int totalPages;
    int pageSize;
    int pageNumber;
    long totalElements;

    @Builder.Default
    List<T> data = Collections.emptyList();
}
