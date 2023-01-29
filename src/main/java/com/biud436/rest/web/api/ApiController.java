package com.biud436.rest.web.api;

import com.biud436.rest.domain.post.MyPostService;
import com.biud436.rest.web.api.dto.CreatePostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.MyPostServiceImpl;
import com.biud436.rest.web.api.dto.PostResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final MyPostService myPostService;

    @Operation(summary = "테스트", description = "테스트 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @GetMapping()
    public ResponseEntity<Map<String, Object>> index() {
        HashMap testResult = new HashMap<String, Object>();
        testResult.put("data", "테스트입니다");

        return ResponseEntity.ok(testResult);
    }

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
    
}
