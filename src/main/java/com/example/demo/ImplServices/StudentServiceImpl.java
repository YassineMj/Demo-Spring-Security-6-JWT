package com.example.demo.ImplServices;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.StudentEntity;
import com.example.demo.requests.StudentRequest;

public interface StudentServiceImpl {
	
	StudentEntity createStudent(StudentRequest studentRequest);
	List<StudentEntity> getAllStudents();
	Optional<StudentEntity> getStudentById(Long id);
	StudentEntity updateStudent(Long id, StudentRequest studentRequest);
	void deleteStudent(Long id);
}
