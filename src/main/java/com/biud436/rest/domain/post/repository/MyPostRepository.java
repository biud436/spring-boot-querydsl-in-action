package com.biud436.rest.domain.post.repository;

import com.biud436.rest.domain.post.entity.MyPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPostRepository extends JpaRepository<MyPost, Long>, MyPostCustomRepository {

}
