package com.example.course_manager.services;

import com.example.course_manager.dto.request.InstructorCreateRequest;
import com.example.course_manager.entities.Instructor;
import com.example.course_manager.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public List<Instructor> findAll(){
        return instructorRepository.findAll();
    }

    public Instructor findById(Long id){
        return instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    public Instructor createInstructor(InstructorCreateRequest request){
        Instructor instructor = new Instructor();
        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());
        return instructorRepository.save(instructor);
    }
}
