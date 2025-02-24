package com.example.authserver.controller.course;

import com.example.authserver.domain.entity.edu.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.authserver.utils.codes.ProductCode.*;

@RestController
@RequestMapping(V1_URI + COURSE_URI)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {

    @GetMapping
    public ResponseEntity<List<Object>> getCourses(@RequestParam Long page, @RequestParam Long size) {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}" + USERS_URI)
    public ResponseEntity<List<Object>> getUsers(@RequestParam Long page, @RequestParam Long size, @PathVariable Long id) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(List.of());
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody Course course) {
        return ResponseEntity.ok(List.of());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCourse(@RequestParam Long courseId) {
        return ResponseEntity.ok().build();
    }

}
