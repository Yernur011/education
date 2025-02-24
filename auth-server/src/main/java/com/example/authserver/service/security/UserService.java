package com.example.authserver.service.security;

import com.example.authserver.domain.dto.auth.AuthorizedUser;
import com.example.authserver.domain.dto.auth.RegistrationDto;
import com.example.authserver.domain.entity.security.UserEntity;
import com.example.authserver.domain.enums.auth.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;


public interface UserService {

    /**
     * Создание или обновление пользователя используя сервис-провайдер
     */
    UserEntity save(OAuth2User userDto, AuthProvider provider);

    /**
     * Создание или обновление пользователя с последующим маппингом в сущность AuthorizedUser
     */
    AuthorizedUser saveAndMap(OAuth2User userDto, AuthProvider provider);

    /**
     * Создание пользователя на основе регистрационных данных. Пользователь будет не активирован.
     *
     * @param userDto данные указанные при регистрации
     */
    UserEntity saveUser(RegistrationDto userDto);

    /**
     * Активация пользователя
     *
     * @param userId   уникальный идентификатор пользователя
     * @param password пароль пользователя
     */
    UserEntity firstActivation(UUID userId, String password);

    /**
     * Создать пользователя и сразу активировать
     */
    UserEntity saveAndActivateUser(RegistrationDto userDto);

    /**
     * Проверить существует ли пользователь с указанным email
     */
    boolean existByEmail(String email);

    /**
     * Найти entity пользователя по email
     */
    UserEntity findByEmail(String email);

    /**
     * Сменить пароль у пользователя с указанным email
     */
    void changePassword(String email, String password);
}