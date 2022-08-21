package com.biud436.rest.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPostRepository extends JpaRepository<MyPost, Long>, MyPostCustomRepository {
}
