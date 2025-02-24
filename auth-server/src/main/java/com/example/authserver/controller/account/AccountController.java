package com.example.authserver.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.authserver.utils.codes.ProductCode.*;

@RestController
@RequestMapping(V1_URI + ACCOUNT_URI)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    @GetMapping(COURSE_URI + ACTIVE_URI)
    public ResponseEntity<List<Object>> getCourses() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(COURSE_URI + FINISHED_URI)
    public ResponseEntity<List<Object>> getCoursesFinished() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(TESTS_URI + ACTIVE_URI)
    public ResponseEntity<List<Object>> getTests() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(TESTS_URI + FINISHED_URI)
    public ResponseEntity<List<Object>> getTestFinished() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(QUESTIONS_URI)
    public ResponseEntity<List<Object>> getQuestions() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(ANSWERS_URI)
    public ResponseEntity<List<Object>> getAnswers() {
        return ResponseEntity.ok(List.of());
    }

    @PutMapping(UPDATE_URI)
    public ResponseEntity<Void> updateUser(){
        return ResponseEntity.ok().build();
    }
}
