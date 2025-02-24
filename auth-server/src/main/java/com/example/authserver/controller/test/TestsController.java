package com.example.authserver.controller.test;

import com.example.authserver.domain.dto.question.QuestionDto;
import com.example.authserver.domain.dto.test.TestAnswers;
import com.example.authserver.domain.dto.test.TestDto;
import com.example.authserver.service.busines.logic.TestService;
import com.example.authserver.service.crud.TestCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.authserver.utils.codes.ProductCode.*;

@RestController
@RequestMapping(V1_URI + TESTS_URI)
@RequiredArgsConstructor
public class TestsController {
    private final TestCrudService testCrudService;
    private final TestService testService;

    @GetMapping
    public ResponseEntity<List<TestDto>> tests(@RequestParam Long page, @RequestParam Long size) {
        return ResponseEntity.ok(testCrudService.findAllTest(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTest(@PathVariable Long id) {
        return ResponseEntity.ok(testCrudService.findDtoById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<List<QuestionDto>> startTest(@PathVariable Long id) {
        return ResponseEntity.ok(testService.startTest(id));
    }
    @PostMapping(FINISH_URI)
    public ResponseEntity<Integer> getTestResult(@RequestBody List<TestAnswers> answers) {
        return ResponseEntity.ok(testService.getScore(answers));
    }

    //TODO дописать
    @GetMapping("/{id}" + USERS_URI)
    public ResponseEntity<Object> getTestById(@PathVariable Long id) {
        return null;
    }
    @PostMapping
    public ResponseEntity<Object> createTest(@RequestBody Object test) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Object> updateTest(@RequestBody Object test) {
        return null;
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteTest(@RequestBody Object test) {
        return null;
    }
}
