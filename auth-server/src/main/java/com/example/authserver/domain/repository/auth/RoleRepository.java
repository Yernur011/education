package com.example.authserver.domain.repository.auth;

import com.example.authserver.domain.entity.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("select r from RoleEntity r where r.code = 'USER_SSO'")
    RoleEntity getDefaultRole();
}