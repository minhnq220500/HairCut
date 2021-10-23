package com.example.haircut.repository;

import com.example.haircut.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
        Optional<Role> findByName(Role name);
}
