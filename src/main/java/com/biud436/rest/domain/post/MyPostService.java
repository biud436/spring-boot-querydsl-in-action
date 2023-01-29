package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.web.api.dto.CreatePostDto;
import com.biud436.rest.web.api.dto.PostResponse;

public interface MyPostService {
    PostResponse<MyPost> findByTitle(String title);
    MyPost save(CreatePostDto postDto);
}
