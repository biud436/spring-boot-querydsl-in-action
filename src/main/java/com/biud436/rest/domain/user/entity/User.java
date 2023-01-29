package com.biud436.rest.domain.user.entity;

import com.biud436.rest.domain.common.entity.BaseTimeEntity;
import com.biud436.rest.domain.post.entity.MyPost;

import com.biud436.rest.domain.profile.entity.Profile;
import lombok.*;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, name = "USER_NAME")
    private String userName;

    @Column(nullable = false, name = "USER_PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<MyPost> posts;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private Profile profile;
}
