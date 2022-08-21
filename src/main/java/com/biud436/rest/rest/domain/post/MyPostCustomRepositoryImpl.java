package com.biud436.rest.rest.domain.post;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biud436.rest.rest.domain.post.dto.MyResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
public class MyPostCustomRepositoryImpl implements MyPostCustomRepository {

    @Autowired
    private EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    public MyPostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public MyResponse<MyPost> findByTitle(String title) {

        List<MyPost> list = jpaQueryFactory.selectFrom(QMyPost.myPost)
//                .where(QMyPost.myPost.title.eq(title))
                .fetch();

        return MyResponse.builder().data(list).message("정상 처리되었습니다").build();
    }
}