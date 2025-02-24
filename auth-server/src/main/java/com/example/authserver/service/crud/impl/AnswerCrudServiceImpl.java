package com.example.authserver.service.crud.impl;

import com.example.authserver.domain.entity.edu.Answer;
import com.example.authserver.domain.repository.edu.AnswerRepository;
import com.example.authserver.service.crud.AnswerCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerCrudServiceImpl implements AnswerCrudService {
    private final AnswerRepository answerRepository;


    @Override
    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElse(null);
    }

}
