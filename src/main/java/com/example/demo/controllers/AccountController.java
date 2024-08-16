package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.RoleDto;
import com.example.demo.DTOs.UserDto;
import com.example.demo.requests.RoleRequest;
import com.example.demo.requests.RolesUserRequest;
import com.example.demo.requests.UserRequest;
import com.example.demo.responses.UserResponse;
import com.example.demo.services.AccountService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/accounts")  // localhost:8080/api/user/accounts
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("get-all-accounts")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> users = accountService.getAllUsers();
		return ResponseEntity.ok(users); // HTTP 200 OK
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
		try {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userRequest, userDto);
			accountService.register(userDto);
			return ResponseEntity.status(201).body("User registered successfully"); // HTTP 201 Created
		} catch (Exception e) {
			return ResponseEntity.status(400).body("User registration failed: " + e.getMessage()); // HTTP 400 Bad Request
		}
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
		try {
			String token = accountService.login(userRequest);
			return ResponseEntity.ok(token); // HTTP 200 OK
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Login failed: " + e.getMessage()); // HTTP 401 Unauthorized
		}
	}
	
	@PostMapping("add-role")
	public ResponseEntity<String> addRole(@RequestBody RoleRequest roleRequest) {
		try {
			RoleDto roleDto = new RoleDto();
			BeanUtils.copyProperties(roleRequest, roleDto);
			accountService.addRole(roleDto);
			return ResponseEntity.ok("Role added successfully"); // HTTP 200 OK
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Failed to add role: " + e.getMessage()); // HTTP 400 Bad Request
		}
	}
	
	@PostMapping("add-role-to-user")
	public ResponseEntity<String> addRoleToUser(@RequestBody RolesUserRequest roleRequest) {
		try {
			accountService.addRoleToUser(roleRequest.getUsername(), roleRequest.getRolename());
			return ResponseEntity.ok("Role added to user successfully"); // HTTP 200 OK
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Failed to add role to user: " + e.getMessage()); // HTTP 400 Bad Request
		}
	}
}

