package com.biud436.rest.web.api;

import com.biud436.rest.common.ResponseData;
import com.biud436.rest.common.TokenInfo;
import com.biud436.rest.domain.post.MyPostService;
import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.user.UserService;
import com.biud436.rest.web.api.dto.CreatePostDto;
import com.biud436.rest.web.api.dto.CreateUserDto;
import com.biud436.rest.web.api.dto.UserLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final MyPostService myPostService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final ApiService apiService;

    @Operation(summary = "포스트 제목으로 조회", description = "특정 제목의 포스트를 검색합니다.")
    @GetMapping("/posts")
    public ResponseEntity<List<MyPost>> getPostByTitle(@RequestParam(value = "title", required = false) String title) {

        return myPostService.findByTitle(title);
    }

    @Operation(summary = "포스트 저장", description = "새로운 포스트를 저장합니다")
    @PostMapping("/posts")
    public ResponseEntity<MyPost> savePost(@RequestBody CreatePostDto postDto) {
        return ResponseEntity.ok(myPostService.save(postDto));
    }

    @Operation(summary = "회원 가입", description = "회원 가입 (테스트)")
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
        return apiService.createUser(createUserDto);
    }

    @Operation(summary = "로그인", description = "로그인 (액세스 토큰 발급")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) throws JsonProcessingException {
        return apiService.login(userLoginDto);
    }

    @Operation(summary = "로그인2", description = "로그인 (액세스 토큰 발급")
    @PostMapping("/login2")
    public ResponseEntity<String> login2(@RequestBody UserLoginDto userLoginDto) throws JsonProcessingException {
        TokenInfo tokenInfo = apiService.login2(userLoginDto);
        ResponseData<TokenInfo> res = ResponseData
                .<TokenInfo>builder()
                .data(tokenInfo)
                .build();

        return ResponseEntity.ok().body(res.toJson());
    }

    @Operation(
            summary = "테스트",
            description = "테스트"
    )
    @SecurityRequirement(name = "bearerAuth")
    @Secured("ROLE_USER")
    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        ResponseData<String> res = ResponseData
                .<String>builder()
                .data("test")
                .build();

        return res.toJson();

    }
}
