package com.biud436.rest.domain.post.repository;

import java.util.List;
import java.util.Optional;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.entity.QMyPost;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.biud436.rest.web.api.dto.PostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@Repository
@RequiredArgsConstructor
public class MyPostCustomRepositoryImpl implements MyPostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<List<MyPost>> findByTitle(String title) {
        JPAQuery<MyPost> qb = jpaQueryFactory.selectFrom(QMyPost.myPost);

        if (title != null && !title.isEmpty()) {
            qb.where(QMyPost.myPost.title.contains(title));
        }

        List<MyPost> list = qb.fetch();

        return Optional.of(list);
    }

}