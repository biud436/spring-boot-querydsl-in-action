package com.biud436.rest.web.api.service;

import com.biud436.rest.common.security.TokenInfo;
import com.biud436.rest.web.api.dto.CreateUserDto;
import com.biud436.rest.web.api.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;

public interface ApiService {
    ResponseEntity<?> createUser(CreateUserDto createUserDto);

    TokenInfo login(UserLoginDto loginDto);
}
