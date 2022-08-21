package com.biud436.rest.web.api.dto;

import java.util.List;

import com.biud436.rest.domain.post.entity.MyPost;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostResponse<T extends MyPost> {
    List<T> data;
    String message;
}
