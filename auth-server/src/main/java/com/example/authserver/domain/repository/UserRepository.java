package com.example.authserver.domain.repository;

import com.example.authserver.domain.entity.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);
}