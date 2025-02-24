package com.example.authserver.domain.dto.test;

import java.util.List;

public record TestAnswers(Long questionId, List<Long> answerIds) {
}
