package com.biud436.rest.web.api.dto;

import java.util.List;

import com.biud436.rest.domain.post.entity.MyPost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "게시글 응답 DTO")
public class PostResponse<T extends MyPost> {

    @Schema(description = "게시글 목록")
    List<T> data;

    @Schema(description = "메시지")
    String message;
}
