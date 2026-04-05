package com.example.course_manager.services;

import com.example.course_manager.dto.request.CourseCreateRequest;
import com.example.course_manager.dto.response.CourseInstructorResponse;
import com.example.course_manager.dto.response.CourseResponse;
import com.example.course_manager.dto.response.CourseResponseV2;
import com.example.course_manager.dto.request.CourseUpdateRequest;
import com.example.course_manager.entities.Course;
import com.example.course_manager.entities.CourseStatus;
import com.example.course_manager.entities.Instructor;
import com.example.course_manager.repositories.CourseRepository;
import com.example.course_manager.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.course_manager.dto.response.PageResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(this::mapToCourseResponse)
                .collect(Collectors.toList());
    }

    public PageResponse<CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction) {
        if (page < 0) {
            page = 0;
        }
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Course> coursePage = courseRepository.findAll(pageable);
        
        Page<CourseResponse> responsePage = coursePage.map(this::mapToCourseResponse);

        return new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                (int) responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isLast()
        );
    }

    public PageResponse<CourseResponseV2> getPagedCoursesByStatus(int page, int size, String sortBy, Sort.Direction direction, CourseStatus status) {
        if (page < 0) {
            page = 0;
        }
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CourseResponseV2> responsePage = courseRepository.findAllProjectedByStatus(status, pageable);

        return new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                (int) responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isLast()
        );
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToCourseResponse(course);
    }

    public CourseResponse createCourse(CourseCreateRequest req) {
        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
                
        Course course = new Course();
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());
        course.setInstructor(instructor);
        
        return mapToCourseResponse(courseRepository.save(course));
    }

    public CourseResponse updateCourse(Long id, CourseUpdateRequest req) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
                
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());
        course.setInstructor(instructor);
        
        return mapToCourseResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
    }

    private CourseResponse mapToCourseResponse(Course course) {
        CourseInstructorResponse instructorResponse = new CourseInstructorResponse(
                course.getInstructor().getId(),
                course.getInstructor().getName()
        );
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                instructorResponse
        );
    }
}
