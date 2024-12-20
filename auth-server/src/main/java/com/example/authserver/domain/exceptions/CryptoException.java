package com.example.authserver.domain.exceptions;

import org.hibernate.service.spi.ServiceException;

public class CryptoException extends ServiceException {

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
