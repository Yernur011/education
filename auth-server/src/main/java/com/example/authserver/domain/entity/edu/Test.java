package com.example.authserver.domain.entity.edu;

import com.example.authserver.domain.entity.core.BusinessEntity;
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
public class Test extends BusinessEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String state;
    String type;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Question> questions = new ArrayList<>();
}
