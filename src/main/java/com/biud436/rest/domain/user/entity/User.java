package com.biud436.rest.domain.user.entity;

import com.biud436.rest.domain.common.entity.BaseTimeEntity;
import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.profile.entity.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, name = "USER_NAME")
    private String userName;

    @Column(nullable = false, name = "USER_PASSWORD")
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private List<MyPost> posts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROFILE_ID")
    private Profile profile;

    @Column(nullable = false, name = "ROLE")
    private String role;
}

