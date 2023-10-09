package com.biud436.rest.domain.post;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.repository.MyPostRepository;
import com.biud436.rest.web.api.dto.CreatePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biud436.rest.web.api.dto.PostResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyPostServiceImpl implements MyPostService {

    private final MyPostRepository postRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<MyPost>> findByTitle(String title) {
        List<MyPost> posts = postRepository.findByTitle(title).orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다"));

        return ResponseEntity.ok().body(posts);
    }

    @Transactional
    public MyPost save(CreatePostDto postDto) {
        return postRepository.save(postDto.toEntity());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<MyPost> findById(Long id) {
        MyPost post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다"));

        return ResponseEntity.ok().body(post);
    }
}
