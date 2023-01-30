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

    private String role;

    @Builder
    public CreateUserDto(String userName, String password, String profileName, String role) {
        this.userName = userName;
        this.password = password;
        this.profileName = profileName;
        this.role = role;
    }

    public User toEntity() {
        User user = User.builder()
                .userName(userName)
                .password(password)
                .role(role)
                .build();

        Profile profile = Profile.builder()
                .user(user)
                .name(profileName)
                .build();

        user.setProfile(profile);

        return user;
    }
}
