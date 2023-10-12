package com.biud436.rest.domain.post.service;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.web.api.dto.CreatePostDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MyPostService {
    ResponseEntity<List<MyPost>> findByTitle(String title);
    MyPost save(CreatePostDto postDto);
    Optional<MyPost> findById(Long id);
}
