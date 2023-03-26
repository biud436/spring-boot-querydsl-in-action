package com.biud436.rest.web.api;

import com.biud436.rest.common.Authority;
import com.biud436.rest.common.JwtTokenProvider;
import com.biud436.rest.common.ResponseData;
import com.biud436.rest.domain.user.UserInfoDto;
import com.biud436.rest.domain.user.UserService;
import com.biud436.rest.web.api.dto.UserLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public ResponseEntity<String> login(UserLoginDto loginDto) throws JsonProcessingException {

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

        String accessToken = jwtTokenProvider.generateToken(userInfoDto.get(), roles);

        Map<String, Object> token = new HashMap<>();
        token.put("accessToken", accessToken);
        
        ResponseData<?> data = new ResponseData<>(200, "로그인 성공", token);

        return ResponseEntity.ok(data.toJson());
    }
}
