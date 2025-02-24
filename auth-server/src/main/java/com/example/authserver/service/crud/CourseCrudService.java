package com.example.authserver.service.crud;

import com.example.authserver.domain.dto.course.CourseDetailsDto;
import com.example.authserver.domain.dto.course.CoursesResponseDto;
import com.example.authserver.domain.entity.edu.Course;

import java.util.List;

public interface CourseCrudService {
    List<CoursesResponseDto> findAll(Long page, Long size);
    List<Course> findAll();
    CourseDetailsDto findById(Long id);
    Course save(Course course);
    Course update(Course course);
    void deleteById(Long id);
}
