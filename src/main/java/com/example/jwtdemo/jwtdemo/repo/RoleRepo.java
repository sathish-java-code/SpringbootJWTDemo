package com.example.jwtdemo.jwtdemo.repo;

import com.example.jwtdemo.jwtdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {


   Optional<Role>  findByName(String name);
}
