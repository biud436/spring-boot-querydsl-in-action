package com.biud436.rest.rest.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.biud436.rest.rest.domain.post.MyPost;
import com.biud436.rest.rest.domain.post.MyPostService;
import com.biud436.rest.rest.domain.post.dto.MyResponse;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private MyPostService myPostService;
    
    @GetMapping()
    public String index() {
        return "API 라우트 테스트";
    }

    // @GetMapping("/posts")
    // public MyResponse<MyPost> findByTitle(String title) {

    //     MyResponse<MyPost> response = MyResponse.builder()
    //             .data(myPostService.findByTitle(title))
    //             .message("정상 처리")
    //             .build();

    //     return response;
    // }

    @GetMapping("/posts")
    @ResponseBody
    public MyResponse<MyPost> findByTitleByUsingQueryDSL(@RequestParam(value = "title") String title) {
        return myPostService.findByTitleByUsingQueryDSL(title);
    }
    
}
