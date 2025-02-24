package com.example.authserver.service.security.impl;

import com.example.authserver.domain.entity.security.UserEntity;
import com.example.authserver.utils.mapper.AuthorizedUserMapper;
import com.example.authserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Попытка загрузить пользователя с username: {}", username);
        UserEntity entity = userRepository.findByEmail(username);
        if (entity == null) {
            log.warn("Пользователь с username = {} не найден", username);
            throw new UsernameNotFoundException("User with username = " + username + " not found");
        }
        log.info("Пользователь с username = {} найден. Преобразование в UserDetails", username);
        return AuthorizedUserMapper.map(entity, null);
    }

}