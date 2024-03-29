package com.biud436.rest.domain.user.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;
    private String userName;
    private String role;
}
