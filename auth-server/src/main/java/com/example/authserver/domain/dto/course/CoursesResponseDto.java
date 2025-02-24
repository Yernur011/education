package com.example.authserver.domain.dto.course;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoursesResponseDto {
    String image;
    List<String> tags = new ArrayList<>();
    String title;
    String description;
}
