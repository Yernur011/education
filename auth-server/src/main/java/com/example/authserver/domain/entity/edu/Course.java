package com.example.authserver.domain.entity.edu;

import com.example.authserver.domain.entity.core.BusinessEntity;
import com.example.authserver.domain.entity.image.Base64Images;
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
public class Course extends BusinessEntity<Long> {
    @Id
    Long id;
    String title;
    String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Base64Images base64Images;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    List<Tags> tags = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    List<Lesson> lessons = new ArrayList<>();

}
