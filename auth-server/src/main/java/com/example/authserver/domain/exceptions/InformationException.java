package com.example.authserver.domain.exceptions;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
public class InformationException extends RuntimeException {

    public InformationException(String message) {
        super(message);
    }

    public InformationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
