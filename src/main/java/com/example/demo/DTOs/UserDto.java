package com.example.demo.DTOs;

import java.util.List;

import com.example.demo.entities.RoleEntity;

import lombok.Data;

@Data
public class UserDto {
	private String username;
    private String password;
    private List<RoleEntity> roles;
    
}
