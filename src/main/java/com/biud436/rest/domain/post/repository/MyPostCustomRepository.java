package com.biud436.rest.domain.post.repository;

import com.biud436.rest.common.PostResponse;
import com.biud436.rest.domain.post.entity.MyPost;

public interface MyPostCustomRepository {
    public PostResponse<MyPost> findByTitle(String title);
}
