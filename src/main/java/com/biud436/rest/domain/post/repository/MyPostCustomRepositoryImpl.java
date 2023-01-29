package com.biud436.rest.domain.post.repository;

import java.util.List;

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
    public PostResponse<MyPost> findOneByTitle(String title) {

        try {
            JPAQuery<MyPost> qb = jpaQueryFactory.selectFrom(QMyPost.myPost);

            if (title != null && !title.isEmpty()) {
                qb.where(QMyPost.myPost.title.contains(title));
            }

            List<MyPost> list = qb.fetch();

            return PostResponse.builder().data(list).message("정상 처리되었습니다").build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "데이터가 존재하지 않습니다.");
        }
    }
    
    @Override
    public PostResponse<MyPost> findOneById(Long id) {

        JPAQuery<MyPost> qb = jpaQueryFactory
            .selectFrom(QMyPost.myPost)
            .where(QMyPost.myPost.id.eq(id));

        List<MyPost> list = qb.fetch();

        return PostResponse.builder().data(list).message("정상 처리되었습니다").build();
    }
}