package com.minhkha.post.mapper;

import com.minhkha.post.dto.response.PostResponse;
import com.minhkha.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse toPostResponse(Post post);
}
