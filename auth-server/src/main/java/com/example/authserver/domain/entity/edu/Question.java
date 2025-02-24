package com.example.authserver.domain.entity.edu;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long questionNumber;
    String text;
    @OneToMany(cascade = CascadeType.ALL)
    List<Answer> answers = new ArrayList<>();
}
