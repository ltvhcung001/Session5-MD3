package com.example.course_manager.services;

import com.example.course_manager.dto.request.StudentCreateRequest;
import com.example.course_manager.entities.Student;
import com.example.course_manager.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public void createStudent(StudentCreateRequest req) {
        Student student = new Student();
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        studentRepository.save(student);
    }
}
