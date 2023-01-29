package com.biud436.rest.domain.post.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.biud436.rest.domain.common.entity.BaseTimeEntity;
import com.biud436.rest.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "my_post")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false, name = "TITLE")
    private String title;

    @Column(nullable = false, name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
