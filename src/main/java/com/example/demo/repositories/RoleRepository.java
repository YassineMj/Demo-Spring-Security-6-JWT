package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.RoleEntity;
import java.util.List;



@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByRoleName(String rolename);
}
