package com.biud436.rest.domain.profile.entity;

import com.biud436.rest.domain.common.entity.BaseTimeEntity;
import com.biud436.rest.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long id;

    @Column(name = "PROFILE_NAME")
    private String name;

    @OneToOne(mappedBy = "profile")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.setProfile(this);
    }
}
