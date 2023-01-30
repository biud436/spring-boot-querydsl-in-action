package com.biud436.rest.domain.user;

import com.biud436.rest.domain.user.entity.User;
import com.biud436.rest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
}
