package com.example.authserver.service.busines.logic;

import com.example.authserver.domain.dto.question.QuestionDto;
import com.example.authserver.domain.dto.test.TestAnswers;
import com.example.authserver.domain.entity.edu.Question;

import java.util.List;

public interface TestService {
    Boolean checkAnswer(TestAnswers testAnswers);
    Integer getScore(List<TestAnswers> questions);
    List<QuestionDto> startTest(Long id);
}
