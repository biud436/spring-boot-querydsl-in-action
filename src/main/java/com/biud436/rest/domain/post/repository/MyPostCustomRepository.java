package com.biud436.rest.domain.post.repository;

import com.biud436.rest.web.api.dto.PostResponse;
import com.biud436.rest.domain.post.entity.MyPost;

import java.util.List;
import java.util.Optional;

public interface MyPostCustomRepository {
    public Optional<List<MyPost>> findByTitle(String title);

}
