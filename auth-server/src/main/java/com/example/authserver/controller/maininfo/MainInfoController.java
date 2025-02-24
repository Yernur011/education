package com.example.authserver.controller.maininfo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.authserver.utils.codes.ProductCode.*;

@RestController
@RequestMapping(V1_URI + MAIN_INFO_URI)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainInfoController {

    @GetMapping(POPULAR_URI + COURSE_URI)
    private ResponseEntity<List<Object>> popularCourse() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(POPULAR_URI + TESTS_URI)
    private ResponseEntity<List<Object>> popularTests() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(POPULAR_URI + QUESTIONS_URI)
    private ResponseEntity<List<Object>> popularQuestions() {
        return ResponseEntity.ok(List.of());
    }
}
