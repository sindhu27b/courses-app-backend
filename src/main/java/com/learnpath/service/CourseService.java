package com.learnpath.service;

import com.learnpath.dto.CourseDto;
import com.learnpath.entity.Course;
import com.learnpath.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepository courseRepository;

    
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public Optional<CourseDto> getCourseById(String id) {
        return courseRepository.findById(id)
                .map(this::toDto);
    }

    
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = Course.builder()
                .id(courseDto.getId())
                .title(courseDto.getTitle())
                .description(courseDto.getDescription())
                .gradient(courseDto.getGradient())
                .externalUrl(courseDto.getExternalUrl())
                .build();
        
        Course saved = courseRepository.save(course);
        return toDto(saved);
    }
    
    public Optional<CourseDto> updateCourse(String id, CourseDto courseDto) {
        return courseRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(courseDto.getTitle());
                    existing.setDescription(courseDto.getDescription());
                    existing.setGradient(courseDto.getGradient());
                    existing.setExternalUrl(courseDto.getExternalUrl());
                    return toDto(courseRepository.save(existing));
                });
    }
    
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }
    
    private CourseDto toDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .gradient(course.getGradient())
                .externalUrl(course.getExternalUrl())
                .build();
    }
}
