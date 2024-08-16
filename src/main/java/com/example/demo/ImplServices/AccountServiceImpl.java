package com.example.demo.ImplServices;

import java.util.List;

import com.example.demo.DTOs.RoleDto;
import com.example.demo.DTOs.UserDto;
import com.example.demo.Responses.UserResponse;

public interface AccountServiceImpl {
	
	void addRole(RoleDto roledto);
	void register(UserDto userDto);
	void addRoleToUser(String username,String rolename);
	UserResponse getUserByUsername(String username);
	List<UserResponse> getAllUsers();
}
