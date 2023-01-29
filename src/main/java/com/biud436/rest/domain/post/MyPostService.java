package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.web.api.dto.CreatePostDto;
import com.biud436.rest.web.api.dto.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MyPostService {
    ResponseEntity<List<MyPost>> findByTitle(String title);
    MyPost save(CreatePostDto postDto);
}
