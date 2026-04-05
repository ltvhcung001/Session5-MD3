package com.example.course_manager.controllers;

import com.example.course_manager.dto.response.ApiResponse;
import com.example.course_manager.dto.request.CourseEnrollmentRequest;
import com.example.course_manager.dto.response.StudentResponse;
import com.example.course_manager.services.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses/{courseId}/enrollments")
public class EnrollmentController {
    private final StudentEnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> enrollStudent(
            @PathVariable Long courseId,
            @RequestBody CourseEnrollmentRequest request) {
        enrollmentService.enrollStudent(courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Student enrolled successfully", null));
    }

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<Void> removeStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        enrollmentService.removeStudentFromCourse(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> searchStudents(
            @PathVariable Long courseId,
            @RequestParam(name = "search", defaultValue = "") String search) {
        List<StudentResponse> students = enrollmentService.searchStudentsInCourse(courseId, search);
        return ResponseEntity.ok(new ApiResponse<>(true, "Students retrieved successfully", students));
    }
}
