package com.example.authserver.service.crud;

import com.example.authserver.domain.entity.edu.Answer;

public interface AnswerCrudService {
    Answer findAnswerById(Long answerId);
}
