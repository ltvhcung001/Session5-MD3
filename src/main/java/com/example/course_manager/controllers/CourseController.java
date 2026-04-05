package com.example.course_manager.controllers;

import com.example.course_manager.dto.response.ApiResponse;
import com.example.course_manager.dto.request.CourseCreateRequest;
import com.example.course_manager.dto.response.CourseResponse;
import com.example.course_manager.dto.response.PageResponse;
import com.example.course_manager.entities.CourseStatus;
import com.example.course_manager.dto.request.CourseUpdateRequest;
import com.example.course_manager.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "ACTIVE") CourseStatus status) {
        
        PageResponse<CourseResponse> pagedCourses = courseService.getPagedCoursesByStatus(page, size, sortBy, direction, status);
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Courses retrieved successfully", pagedCourses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addCourse(@RequestBody CourseCreateRequest request) {
        courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Course created successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateRequest request) {
        courseService.updateCourse(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Course updated successfully", null));
    }
}
