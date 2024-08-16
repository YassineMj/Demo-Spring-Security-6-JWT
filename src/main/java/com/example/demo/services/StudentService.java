package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ImplServices.StudentServiceImpl;
import com.example.demo.entities.StudentEntity;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.requests.StudentRequest;

@Service
public class StudentService implements StudentServiceImpl{

	@Autowired
    private StudentRepository studentRepository;
	
	@Override
	public StudentEntity createStudent(StudentRequest studentRequest) {
		StudentEntity student = new StudentEntity();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setBirthDate(studentRequest.getBirthDate());
        return studentRepository.save(student);
	}

	@Override
	public List<StudentEntity> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Optional<StudentEntity> getStudentById(Long id) {
		return studentRepository.findById(id);
	}

	@Override
	public StudentEntity updateStudent(Long id, StudentRequest studentRequest) {
		Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            StudentEntity student = optionalStudent.get();
            student.setFirstName(studentRequest.getFirstName());
            student.setLastName(studentRequest.getLastName());
            student.setBirthDate(studentRequest.getBirthDate());
            return studentRepository.save(student);
        }
        return null;
	}

	@Override
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}

}
