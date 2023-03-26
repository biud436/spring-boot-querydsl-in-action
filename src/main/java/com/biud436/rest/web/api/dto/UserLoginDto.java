package com.biud436.rest.web.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "사용자 로그인 DTO")
@Builder
public class UserLoginDto {
    @Schema(description = "사용자 이름", example = "test")
    private String userName;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    UserLoginDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
