package com.biud436.rest.domain.user;

import com.biud436.rest.common.Authority;
import com.biud436.rest.domain.user.entity.User;
import com.biud436.rest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public Optional<UserInfoDto> validateUser(String userName, String password) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return Optional.ofNullable(UserInfoDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .role(Authority.USER.getValue())
                .build());
    }

    public boolean isExistUser(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

}
