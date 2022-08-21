package com.biud436.rest.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.biud436.rest.domain.post.entity.MyPost;
import com.biud436.rest.domain.post.MyPostService;
import com.biud436.rest.common.PostResponse;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private MyPostService myPostService;
    
    @GetMapping()
    public String index() {
        return "API 라우트 테스트";
    }

    @GetMapping("/posts")
    public PostResponse<MyPost> findByTitleByUsingQueryDSL(@RequestParam(value = "title", required = false) String title) {

        System.out.println(title);

        PostResponse<MyPost> response = myPostService.findByTitleByUsingQueryDSL(title);

        return response;
    }
    
}
