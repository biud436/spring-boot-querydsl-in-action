package com.biud436.rest.domain.profile.repository;

import com.biud436.rest.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
