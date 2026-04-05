package com.example.course_manager.controllers;

import com.example.course_manager.dto.response.ApiResponse;
import com.example.course_manager.dto.request.InstructorCreateRequest;
import com.example.course_manager.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createInstructor(@RequestBody InstructorCreateRequest request) {
        instructorService.createInstructor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Instructor created successfully", null));
    }
}