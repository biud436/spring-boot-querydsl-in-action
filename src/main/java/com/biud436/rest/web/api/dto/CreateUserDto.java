package com.biud436.rest.web.api.dto;

import com.biud436.rest.common.Authority;
import com.biud436.rest.domain.profile.entity.Profile;
import com.biud436.rest.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "사용자 생성 DTO")
public class CreateUserDto {
    private String userName;
    private String password;

    private String profileName;

    @Schema(description = "권한", example = "ROLE_USER", hidden = true)
    private String role = Authority.USER.getValue();

    @Builder
    public CreateUserDto(String userName, String password, String profileName) {
        this.userName = userName;
        this.password = password;
        this.profileName = profileName;
        this.role = Authority.USER.getValue();
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
