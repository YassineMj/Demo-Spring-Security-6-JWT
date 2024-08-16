package com.example.demo.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.RoleEntity;
import com.example.demo.entities.UserEntity;

public class AuthUser implements UserDetails {

	private UserEntity userEntity;
	
	public AuthUser(UserEntity userEntity) {
		this.userEntity=userEntity;
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retrieve user roles from UserEntity
        List<RoleEntity> userRoles = userEntity.getRoles(); // Assuming UserEntity has a getRoles() method

        List<SimpleGrantedAuthority> authorities = new ArrayList();
        for (RoleEntity role : userRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" +role.getRoleName().toString()));
        }
        return authorities;
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userEntity.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
