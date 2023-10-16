package com.biud436.rest.web.api.dto;

import com.biud436.rest.domain.post.entity.MyPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@Schema(description = "게시글 생성 DTO")
@NoArgsConstructor
public class CreatePostDto {

    @Size(min = 1, max = 100)
    @Schema(description = "제목", example = "제목입니다.")
    private String title;

    @Size(min = 1, max = 4000)
    @Schema(description = "내용", example = "내용입니다.")
    private String content;

    @Builder
    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public MyPost toEntity() {
        return MyPost.builder()
                .content(content)
                .title(title)
                .build();
    }
}
