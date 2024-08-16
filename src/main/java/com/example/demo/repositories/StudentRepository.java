package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
	
} 