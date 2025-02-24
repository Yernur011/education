package com.example.authserver.domain.entity.edu;

import com.example.authserver.domain.entity.core.BusinessEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson extends BusinessEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long lessonNumber;
    String title;
    String videoUrl;
    @Lob
    String bodyText;
    String status;
    Boolean isCompleted;
}
