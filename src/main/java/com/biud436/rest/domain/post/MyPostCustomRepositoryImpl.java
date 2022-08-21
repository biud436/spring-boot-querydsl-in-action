package com.biud436.rest.domain.post;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biud436.rest.domain.post.dto.MyResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

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