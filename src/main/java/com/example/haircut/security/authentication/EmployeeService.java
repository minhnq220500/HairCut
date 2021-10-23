package com.example.haircut.security.authentication;

import com.example.haircut.model.Employee;
import com.example.haircut.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class EmployeeService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByEmpEmail(email);

        if (employee == null) {
            throw new UsernameNotFoundException("EMAIL_NOT_FOUND");
        }

        return new EmployeeDetail(employee);
    }
}
