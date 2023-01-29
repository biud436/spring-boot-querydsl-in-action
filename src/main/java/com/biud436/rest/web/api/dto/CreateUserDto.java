package com.biud436.rest.web.api.dto;

import com.biud436.rest.domain.profile.entity.Profile;
import com.biud436.rest.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class CreateUserDto {
    private String userName;
    private String password;

    private String profileName;

    @Builder
    public CreateUserDto(String userName, String password, String profileName) {
        this.userName = userName;
        this.password = password;
        this.profileName = profileName;
    }

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .password(password)
                .profile(
                        Profile.builder()
                                .name(profileName)
                                .build()
                )
                .build();
    }
}
