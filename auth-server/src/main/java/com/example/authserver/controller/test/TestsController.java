package com.example.authserver.controller.test;

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

    @GetMapping
    public ResponseEntity<List<Object>> tests(@RequestParam Long page, @RequestParam Long size) {
        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTest( @RequestParam Long page, @RequestParam Long size, @PathVariable Long id) {
        return null;
    }
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
