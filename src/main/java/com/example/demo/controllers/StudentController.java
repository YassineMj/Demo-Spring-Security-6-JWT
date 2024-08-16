package com.example.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.StudentEntity;
import com.example.demo.requests.StudentRequest;
import com.example.demo.services.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("add-student")
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            StudentEntity student = studentService.createStudent(studentRequest);
            return ResponseEntity.status(201).body(student); // HTTP 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // HTTP 400 Bad Request
        }
    }

    @GetMapping("get-all-students")
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        List<StudentEntity> students = studentService.getAllStudents();
        return ResponseEntity.ok(students); // HTTP 200 OK
    }

    @GetMapping("get-student/{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable Long id) {
        Optional<StudentEntity> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
    }

    @PutMapping("update-student/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        try {
            StudentEntity updatedStudent = studentService.updateStudent(id, studentRequest);
            return ResponseEntity.ok(updatedStudent); // HTTP 200 OK
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // HTTP 400 Bad Request
        }
    }

    @DeleteMapping("delete-student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }
}
