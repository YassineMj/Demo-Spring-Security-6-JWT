package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.mbeans.MBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.RoleDto;
import com.example.demo.DTOs.UserDto;
import com.example.demo.entities.RoleEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.models.AuthUser;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.UserRequest;
import com.example.demo.responses.UserResponse;
import com.fasterxml.jackson.databind.BeanProperty;


@Service
public class AccountService implements com.example.demo.ImplServices.AccountServiceImpl , UserDetailsService{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTService jwtService;
	
	
	@Override
	public void addRole(RoleDto roledto) {
		RoleEntity roleEntity= new RoleEntity();
		BeanUtils.copyProperties(roledto, roleEntity);
		roleRepository.save(roleEntity);
	}

	private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);
	
	@Override
	public void register(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		
		userEntity.setPassword(encoder.encode(userEntity.getPassword()));
		
		userRepository.save(userEntity);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		RoleEntity roleEntity=roleRepository.findByRoleName(rolename);
		UserEntity userEntity=userRepository.findByUsername(username);
		
		userEntity.getRoles().add(roleEntity);
		userRepository.save(userEntity);
	}

	@Override
	public UserResponse getUserByUsername(String username) {
		UserEntity userEntity= userRepository.findByUsername(username);
		
		UserResponse userResponse=new UserResponse();
		
		BeanUtils.copyProperties(userEntity, userResponse);
		return userResponse;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<UserEntity> listUserEntities=userRepository.findAll();
		List<UserResponse> listResponses=new ArrayList<>();
		
		UserResponse ur;
		for(UserEntity u : listUserEntities) {
			ur=new UserResponse();
			BeanUtils.copyProperties(u, ur);
			listResponses.add(ur);
		}
		
		return listResponses;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		System.out.println(username);
		
		if(userEntity==null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("user not found");
		}
		
		return new AuthUser(userEntity);
	}

	public String login(UserRequest userRequest) {
		org.springframework.security.core.Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(userRequest.getUsername());
		}
		
		return "fail";
	}



}
