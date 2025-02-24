package com.example.authserver.service.security.redis;

import com.example.authserver.domain.dto.auth.RegistrationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisRegistrationStore implements RegistrationStore {

    private final static String SESSION_ID_TO_REG_DATA = "registration_store:session_id_to_reg_data:";

    private final int expiresIn;
    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> store;
    private final ObjectMapper objectMapper;

    public RedisRegistrationStore(int expiresIn, StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.store = redisTemplate.opsForValue();
        this.objectMapper = objectMapper;
        this.expiresIn = expiresIn;
    }

    @Override
    public void save(RegistrationDto dto, String sessionId) {
        try {
            String stringDto = objectMapper.writeValueAsString(dto);
            store.set(SESSION_ID_TO_REG_DATA + sessionId, stringDto, expiresIn, TimeUnit.SECONDS);
            log.info("Session id {} saved to Redis", sessionId);
        } catch (JsonProcessingException e) {
            log.error("Error serializing RegistrationDto for session id {}", sessionId, e);
            throw new RuntimeException("Error serializing RegistrationDto", e);
        } catch (Exception e) {
            log.error("Error saving RegistrationDto for session id {}", sessionId, e);
            throw new RuntimeException("Error saving RegistrationDto", e);
        }
    }

    @Override
    public RegistrationDto take(String sessionId) {
        try {
            String stringDto = store.get(SESSION_ID_TO_REG_DATA + sessionId);
            if (stringDto == null) {
                log.warn("No data found for session id {}", sessionId);
                return null;
            }
            redisTemplate.delete(SESSION_ID_TO_REG_DATA + sessionId);
            return objectMapper.readValue(stringDto, RegistrationDto.class);
        } catch (IOException e) {
            log.error("Error deserializing RegistrationDto for session id {}", sessionId, e);
            throw new RuntimeException("Error deserializing RegistrationDto", e);
        } catch (Exception e) {
            log.error("Error retrieving RegistrationDto for session id {}", sessionId, e);
            throw new RuntimeException("Error retrieving RegistrationDto", e);
        }
    }
}
