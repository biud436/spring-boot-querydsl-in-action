package com.biud436.rest.domain.user.repository;

import com.biud436.rest.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
