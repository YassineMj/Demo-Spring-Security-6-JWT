package com.example.demo.requests;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentRequest {

	private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
