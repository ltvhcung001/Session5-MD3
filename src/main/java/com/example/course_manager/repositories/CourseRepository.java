package com.example.course_manager.repositories;

import com.example.course_manager.entities.Course;
import com.example.course_manager.entities.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByIdAndStatus(Long id, CourseStatus status);

    @Query("SELECT c FROM Course c WHERE c.status = :status")
    Page<Course> findAllByStatus(@Param("status") CourseStatus status, Pageable pageable);
}
