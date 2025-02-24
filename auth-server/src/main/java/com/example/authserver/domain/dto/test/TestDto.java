package com.example.authserver.domain.dto.test;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDto {
    Long id;
    String title;
    String state;
    String type;
}
