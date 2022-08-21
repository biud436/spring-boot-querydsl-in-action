package com.biud436.rest.domain.post.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.entity.QMyPost;
import com.biud436.rest.domain.post.repository.MyPostCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biud436.rest.common.PostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class MyPostCustomRepositoryImpl implements MyPostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MyPostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PostResponse<MyPost> findByTitle(String title) {

        JPAQuery<MyPost> qb = jpaQueryFactory.selectFrom(QMyPost.myPost);

        if(title != null) {
            qb.where(QMyPost.myPost.title.contains(title));
        }

        List<MyPost> list = qb.fetch();

        return PostResponse.builder().data(list).message("정상 처리되었습니다").build();
    }
}