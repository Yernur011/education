package com.example.authserver.domain.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
@Table(schema = "sso", name = "token_black-list")
public class TokenBlackList {
    @Id
    UUID id;
    String token;
}
