package com.biud436.rest.rest.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biud436.rest.rest.domain.post.dto.MyResponse;

public interface MyPostRepository extends JpaRepository<MyPost, Long>, MyPostCustomRepository {
}
