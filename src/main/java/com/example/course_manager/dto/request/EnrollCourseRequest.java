package com.example.course_manager.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollCourseRequest {
    Long id;
    String studentName;
    Long courseId;
}
