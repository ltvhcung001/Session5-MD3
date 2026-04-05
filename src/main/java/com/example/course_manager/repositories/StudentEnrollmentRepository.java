package com.example.course_manager.repositories;

import com.example.course_manager.entities.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
    Optional<StudentEnrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);
    List<StudentEnrollment> findByCourseId(Long courseId);
}
