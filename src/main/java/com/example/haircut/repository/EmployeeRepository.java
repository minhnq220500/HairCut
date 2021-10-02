package com.example.haircut.repository;

import com.example.haircut.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
    Optional<Employee> findEmployeeByEmpEmail(String empEmail);
}
