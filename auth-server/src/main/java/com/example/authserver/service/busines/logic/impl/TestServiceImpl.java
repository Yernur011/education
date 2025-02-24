package com.example.authserver.service.busines.logic.impl;

import com.example.authserver.domain.dto.AnswerDto;
import com.example.authserver.domain.dto.question.QuestionDto;
import com.example.authserver.domain.dto.test.TestAnswers;
import com.example.authserver.domain.entity.edu.Answer;
import com.example.authserver.service.busines.logic.TestService;
import com.example.authserver.service.crud.AnswerCrudService;
import com.example.authserver.service.crud.TestCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.authserver.utils.codes.ErrorCode.TEST_NOT_FOUND;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestServiceImpl implements TestService {
    private final AnswerCrudService answerCrudService;
    private final TestCrudService testCrudService;


    @Override
    public Boolean checkAnswer(TestAnswers testAnswers) {
        Long questionId = testAnswers.questionId();
        List<Long> answerId = testAnswers.answerIds();

        List<Answer> list = answerId.stream()
                .map(answerCrudService::findAnswerById)
                .toList();

        for (Answer answer : list) {
            if (!answer.getQuestion().getId().equals(questionId)) {
                throw new RuntimeException("Answer does not belong to the question");
            }
        }

        List<Boolean> avgPoints = list.stream().map(Answer::getCorrect)
                .filter(Boolean.FALSE::equals)
                .toList();//TODO calc avg points

        return avgPoints.isEmpty();
    }

    @Override
    public Integer getScore(List<TestAnswers> questions) {
        return questions.stream()
                .map(this::checkAnswer)
                .mapToInt(ans -> ans ? 1 : 0)
                .sum();
    }

    @Override
    public List<QuestionDto> startTest(Long id) {
        return testCrudService.findById(id)
                .map(test -> test.getQuestions().stream()
                        .map(question -> {
                            List<AnswerDto> ansDto = question.getAnswers()
                                    .stream()
                                    .map(answer -> new AnswerDto(answer.getId(), answer.getText())).toList();

                            return new QuestionDto(question.getId(),
                                    question.getQuestionNumber(),
                                    question.getText(),
                                    ansDto);
                        })
                        .toList())
                .orElseThrow(() -> new NoSuchElementException(TEST_NOT_FOUND));
    }


}
