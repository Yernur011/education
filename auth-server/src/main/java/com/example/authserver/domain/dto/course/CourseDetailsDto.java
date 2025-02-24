package com.example.authserver.domain.dto.course;

import com.example.authserver.domain.entity.edu.Lesson;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDetailsDto {
    Long id;
    String title;
    String description;
    String image;
    List<String> tags = new ArrayList<>();
    List<Lesson> lessons = new ArrayList<>();
}
