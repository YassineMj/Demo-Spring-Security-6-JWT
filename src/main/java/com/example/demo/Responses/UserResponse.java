package com.example.demo.Responses;

import java.util.List;

import com.example.demo.entities.RoleEntity;

import lombok.Data;

@Data
public class UserResponse {
	
	private String username;
    private List<RoleEntity> roles;
    
	
}
