package com.example.course_manager.services;

import com.example.course_manager.dto.request.CourseEnrollmentRequest;
import com.example.course_manager.dto.response.CourseEnrollmentResponse;
import com.example.course_manager.dto.response.StudentResponse;
import com.example.course_manager.entities.Course;
import com.example.course_manager.entities.CourseStatus;
import com.example.course_manager.entities.Student;
import com.example.course_manager.entities.StudentEnrollment;
import com.example.course_manager.repositories.CourseRepository;
import com.example.course_manager.repositories.StudentEnrollmentRepository;
import com.example.course_manager.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentEnrollmentService {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseEnrollmentResponse enrollStudent(Long courseId, CourseEnrollmentRequest request) {
        if (studentEnrollmentRepository.existsByCourseIdAndStudentId(courseId, request.getStudentId())) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new RuntimeException("Course is not ACTIVE");
        }

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment = studentEnrollmentRepository.save(enrollment);

        return new CourseEnrollmentResponse(
                student.getId(),
                course.getId(),
                enrollment.getEnrolledAt());
    }

    public void removeStudentFromCourse(Long courseId, Long studentId) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new RuntimeException("Student is not enrolled in this course"));
        studentEnrollmentRepository.delete(enrollment);
    }

    public List<StudentResponse> searchStudentsInCourse(Long courseId, String searchName) {
        return studentEnrollmentRepository.findByCourseId(courseId).stream()
                .map(StudentEnrollment::getStudent)
                .filter(student -> student.getName().toLowerCase().contains(searchName.toLowerCase()))
                .map(s -> new StudentResponse(s.getId(), s.getName(), s.getEmail()))
                .collect(Collectors.toList());
    }
}
