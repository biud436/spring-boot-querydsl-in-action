package com.biud436.rest.web.api;

import com.biud436.rest.common.TokenInfo;
import com.biud436.rest.web.api.dto.CreateUserDto;
import com.biud436.rest.web.api.dto.UserLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ApiService {
    ResponseEntity<?> createUser(CreateUserDto createUserDto);

    TokenInfo login(UserLoginDto loginDto);
}
