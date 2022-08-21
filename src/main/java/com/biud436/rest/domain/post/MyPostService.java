package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.repository.MyPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biud436.rest.web.api.dto.PostResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyPostService {

    private final MyPostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponse<MyPost> findByTitleByUsingQueryDSL(String title) {
        return postRepository.findByTitle(title);
    }

}
