package com.example.authserver.utils.mapper;

import com.example.authserver.domain.dto.auth.AuthorizedUser;
import com.example.authserver.domain.entity.security.AuthorityEntity;
import com.example.authserver.domain.entity.security.RoleEntity;
import com.example.authserver.domain.entity.security.UserEntity;
import com.example.authserver.domain.type.AuthProvider;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorizedUserMapper {

    public AuthorizedUser map(UserEntity entity, AuthProvider provider) {
        // получаем список привилегий из сущности и помещаем его в параметр builder
        List<GrantedAuthority> authorities = getUserAuthorities(entity);
        return AuthorizedUser.builder(entity.getEmail(), entity.getPasswordHash(), authorities)
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .secondName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .birthday(entity.getBirthday())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }

    // получаем список привилегий из сущности и преобразовываем каждый код привилегии в объект SimpleGrantedAuthority
    public List<GrantedAuthority> getUserAuthorities(UserEntity entity) {
        return entity.getRoles().stream()
                .filter(RoleEntity::getActive)
                .flatMap(role -> role.getAuthorities().stream())
                .filter(AuthorityEntity::getActive)
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toList());
    }
}