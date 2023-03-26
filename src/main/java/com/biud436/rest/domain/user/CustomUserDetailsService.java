package com.biud436.rest.domain.user;

import com.biud436.rest.domain.user.entity.User;
import com.biud436.rest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }

        User userEntity = user.get();

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(userEntity.getUsername());
        customUserDetails.setPassword(userEntity.getPassword());
        customUserDetails.setAuthorities(
                userEntity.getAuthorities()
        );

        customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpired(true);

        return customUserDetails;
    }
}
