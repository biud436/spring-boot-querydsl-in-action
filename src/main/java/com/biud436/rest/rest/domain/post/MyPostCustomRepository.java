package com.biud436.rest.rest.domain.post;

import com.biud436.rest.rest.domain.post.dto.MyResponse;

public interface MyPostCustomRepository {
    public MyResponse<MyPost> findByTitle(String title);
}
