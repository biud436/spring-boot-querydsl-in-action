package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.dto.MyResponse;

public interface MyPostCustomRepository {
    public MyResponse<MyPost> findByTitle(String title);
}
