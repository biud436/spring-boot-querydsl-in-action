package com.biud436.rest.web;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainIndexController {

    @Operation(hidden = true)
    @GetMapping("/")
    public String index() {
        return "스프링 부트 프로젝트 테스트";
    }

}
