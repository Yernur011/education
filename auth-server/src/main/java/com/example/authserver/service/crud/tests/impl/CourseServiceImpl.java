package com.example.authserver.service.crud.tests.impl;

import com.example.authserver.domain.dto.course.CoursesResponseDto;
import com.example.authserver.domain.entity.edu.Course;
import com.example.authserver.domain.entity.edu.Tags;
import com.example.authserver.domain.exceptions.busines.InvalidValueException;
import com.example.authserver.domain.repository.edu.CourseRepository;
import com.example.authserver.service.crud.tests.CourseCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.authserver.utils.codes.ErrorCode.COURSE_NOT_FOUND;
import static com.example.authserver.utils.codes.ErrorCode.UPDATE_COURSE_EXCEPTION;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseCrudService {
    private final CourseRepository courseRepository;
    @Override
    public List<CoursesResponseDto> findAll(Long page, Long size) {
        return courseRepository.findAll(PageRequest.of(page.intValue(), size.intValue()))
                .getContent()
                .stream()
                .map(course ->
                        new CoursesResponseDto(
                                course.getBase64Images().getBase64Image(),
                                course.getTags().stream().map(Tags::getName).toList(),
                                course.getTitle(),
                                course.getDescription()))
                .toList();
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(COURSE_NOT_FOUND));
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course update(Course course) {
        return Optional.of(course.getId())
                .map(id -> save(course))
                .orElseThrow(() -> new InvalidValueException(UPDATE_COURSE_EXCEPTION));
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}
