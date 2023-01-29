package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.repository.MyPostRepository;
import com.biud436.rest.web.api.dto.CreatePostDto;
import org.springframework.stereotype.Service;

import com.biud436.rest.web.api.dto.PostResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyPostServiceImpl implements MyPostService {

    private final MyPostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponse<MyPost> findByTitle(String title) {
        return postRepository.findOneByTitle(title);
    }

    @Transactional
    public MyPost save(CreatePostDto postDto) {
        return postRepository.save(postDto.toEntity());
    }
}
