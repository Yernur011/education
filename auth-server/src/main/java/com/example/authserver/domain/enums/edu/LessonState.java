package com.example.authserver.domain.enums.edu;

import com.example.authserver.domain.enums.auth.SSOScope;

public enum LessonState {
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершен");

    private final String value;

    LessonState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
