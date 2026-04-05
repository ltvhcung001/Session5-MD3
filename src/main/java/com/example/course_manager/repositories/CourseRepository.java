package com.example.course_manager.repositories;

import com.example.course_manager.entities.Course;
import com.example.course_manager.entities.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByIdAndStatus(Long id, CourseStatus status);
}
