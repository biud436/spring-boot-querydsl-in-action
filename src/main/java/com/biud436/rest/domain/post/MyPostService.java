package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.repository.MyPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biud436.rest.common.PostResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyPostService {
    
    @Autowired
    private MyPostRepository postRepository;

//     @PostConstruct
//     public void init() {
//         postRepository.save(MyPost.builder().title("title1").build());
//         postRepository.save(MyPost.builder().title("title2").build());
//         postRepository.save(MyPost.builder().title("title3").build());
//     }

    public PostResponse<MyPost> findByTitleByUsingQueryDSL(String title) {
        return postRepository.findByTitle(title);
    }

}
