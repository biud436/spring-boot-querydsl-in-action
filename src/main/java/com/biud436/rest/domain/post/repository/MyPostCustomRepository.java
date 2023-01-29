package com.biud436.rest.domain.post.repository;

import com.biud436.rest.web.api.dto.PostResponse;
import com.biud436.rest.domain.post.entity.MyPost;

public interface MyPostCustomRepository {
    public PostResponse<MyPost> findOneByTitle(String title);

    public PostResponse<MyPost> findOneById(Long id);
}
