package com.biud436.rest.rest.domain.post;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biud436.rest.rest.domain.post.dto.MyResponse;

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

    public MyResponse<MyPost> findByTitleByUsingQueryDSL(String title) {
        return postRepository.findByTitle(title);
    }

}
