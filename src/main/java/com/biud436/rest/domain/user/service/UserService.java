package com.biud436.rest.domain.user.service;

import com.biud436.rest.domain.user.dto.UserInfoDto;
import com.biud436.rest.domain.user.entity.User;

import java.util.Optional;

public interface UserService {

    void createUser(User user);

    Optional<UserInfoDto> validateUser(String username, String password);

    boolean isExistUser(String userName);

    User findByUserName(String userName);
}
