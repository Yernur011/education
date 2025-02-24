package com.example.authserver.controller.course;

import com.example.authserver.domain.dto.course.CourseDetailsDto;
import com.example.authserver.domain.dto.course.CoursesResponseDto;
import com.example.authserver.service.crud.CourseCrudService;
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
    private final CourseCrudService courseCrudService;

    @GetMapping
    public ResponseEntity<List<CoursesResponseDto>> getCourses(@RequestParam Long page, @RequestParam Long size) {
        return ResponseEntity.ok(courseCrudService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsDto> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseCrudService.findById(id));
    }

    @GetMapping("/{id}" + USERS_URI)
    public ResponseEntity<List<Object>> getUsers(@RequestParam Long page, @RequestParam Long size, @PathVariable Long id) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody Object course) {
        return ResponseEntity.ok(List.of());
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody Object course) {
        return ResponseEntity.ok(List.of());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCourse(@RequestParam Long courseId) {
        courseCrudService.deleteById(courseId);
        return ResponseEntity.ok().build();
    }

}
