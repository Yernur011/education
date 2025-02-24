package com.example.authserver.domain.repository.auth;

import com.example.authserver.domain.entity.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);
}