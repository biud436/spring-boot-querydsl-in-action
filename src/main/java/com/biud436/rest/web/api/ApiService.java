package com.biud436.rest.web.api;

import com.biud436.rest.common.Authority;
import com.biud436.rest.common.JwtTokenProvider;
import com.biud436.rest.domain.user.UserInfoDto;
import com.biud436.rest.domain.user.UserService;
import com.biud436.rest.web.api.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public ResponseEntity<Map<String, Object>> login(UserLoginDto loginDto) {

        List<String> roles = new ArrayList<>();
        roles.add(Authority.USER.getValue());

        String password = loginDto.getPassword();
        int lenOfPassword = password.length();

        if (loginDto.getUserName() == null || password == null) {
            throw new IllegalArgumentException("아이디와 비밀번호를 입력해주세요.");
        }

        if (lenOfPassword < 8 || lenOfPassword > 16) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 16자 이하로 입력해주세요.");
        }

        // 유저 정보 가져오기
        Optional<UserInfoDto> userInfoDto = userService.validateUser(loginDto.getUserName(), loginDto.getPassword());

        Map<String, Object> map = new HashMap<>();

        String accessToken = jwtTokenProvider.generateToken(userInfoDto.get(), roles);
        map.put("accessToken", accessToken);

        return ResponseEntity.ok().body(map);
    }
}
