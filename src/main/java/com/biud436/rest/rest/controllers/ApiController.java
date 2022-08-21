package com.biud436.rest.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping()
    public String index() {
        return "API 라우트 테스트";
    }
    
}
