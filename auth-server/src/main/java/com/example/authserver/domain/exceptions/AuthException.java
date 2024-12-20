package com.example.authserver.domain.exceptions;

import com.example.authserver.domain.type.AuthErrorCode;
import lombok.Getter;

import javax.security.sasl.AuthenticationException;


@Getter
public class AuthException extends AuthenticationException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public AuthException(String msg, AuthErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public AuthException(AuthErrorCode errorCode) {
        super(null);
        this.errorCode = errorCode;
    }
}
