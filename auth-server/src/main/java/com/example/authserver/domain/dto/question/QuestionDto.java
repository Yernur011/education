package com.example.authserver.domain.dto.question;

import com.example.authserver.domain.dto.AnswerDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {
    Long id;
    Long questionNumber;
    String text;
    List<AnswerDto> answers = new ArrayList<>();

}
