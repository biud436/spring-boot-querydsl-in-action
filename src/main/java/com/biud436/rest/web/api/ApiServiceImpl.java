package com.biud436.rest.web.api;

import com.biud436.rest.common.*;
import com.biud436.rest.domain.user.UserInfoDto;
import com.biud436.rest.domain.user.UserService;
import com.biud436.rest.domain.user.entity.User;
import com.biud436.rest.web.api.dto.CreateUserDto;
import com.biud436.rest.web.api.dto.UserLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {

        String username = createUserDto.getUserName();
        String password = createUserDto.getPassword();

        if (username == null) {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }

        if (userService.isExistUser(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (password == null) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        int lenOfPassword = password.length();
        if (lenOfPassword < 8 || lenOfPassword > 16) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 16자 이하로 입력해주세요.");
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$";
        if (!password.matches(regex)) {
            throw new IllegalArgumentException("비밀번호는 특수 문자 및 숫자, 대소문자를 포함해야 합니다.");
        }

        User user = createUserDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.createUser(user);

        return ResponseEntity.ok().build();
    }

    @NonNull
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @LogTime
    public TokenInfo login(UserLoginDto loginDto) {
        List<String> roles = new ArrayList<>();

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

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}
