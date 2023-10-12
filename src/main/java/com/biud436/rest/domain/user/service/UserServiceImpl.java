package com.biud436.rest.domain.user.service;

import com.biud436.rest.common.security.Authority;
import com.biud436.rest.domain.user.dto.UserInfoDto;
import com.biud436.rest.domain.user.entity.User;
import com.biud436.rest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<UserInfoDto> validateUser(String userName, String password) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!isMatchPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return Optional.ofNullable(UserInfoDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .role(Authority.USER.getValue())
                .build());
    }

    /**
     * 비밀번호가 일치하는지 확인합니다.
     *
     * @param password
     * @param encodedPassword
     * @return
     */
    private boolean isMatchPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public boolean isExistUser(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }


    public User findByUserName(String userName) {

        System.out.println("userName : " + userName);

        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

}
