package com.biud436.rest.web.api;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.MyPostService;
import com.biud436.rest.web.api.dto.PostResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final MyPostService myPostService;
    
    @GetMapping()
    public String index() {
        return "API 라우트 테스트";
    }

    @GetMapping("/posts")
    public PostResponse<MyPost> findByTitleByUsingQueryDSL(@RequestParam(value = "title", required = false) String title) {

        PostResponse<MyPost> response = myPostService.findByTitleByUsingQueryDSL(title);

        return response;
    }
    
}
