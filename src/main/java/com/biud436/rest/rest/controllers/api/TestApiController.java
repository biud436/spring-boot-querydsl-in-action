package com.biud436.rest.rest.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController {
    
    @RequestMapping("/")
    public String index() {
        return "스프링 부트 프로젝트 테스트";
    }

}
