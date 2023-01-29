package com.biud436.rest.web.api.dto;

import com.biud436.rest.domain.post.entity.MyPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostDto {
    private String title;
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
