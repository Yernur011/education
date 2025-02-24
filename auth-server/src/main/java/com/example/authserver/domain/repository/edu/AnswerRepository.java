package com.example.authserver.domain.repository.edu;

import com.example.authserver.domain.entity.edu.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
