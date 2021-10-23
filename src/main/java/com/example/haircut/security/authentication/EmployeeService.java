package com.example.haircut.security.authentication;

import com.example.haircut.model.Customer;
import com.example.haircut.model.Employee;
import com.example.haircut.repository.CustomerRepository;
import com.example.haircut.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByEmpEmail(email);

        if (employee == null) {
            Customer customer = customerRepository.findCustomerByCusEmail(email);
            if (customer != null) {
                return new CustomerDetail(customer);
            }
            throw new UsernameNotFoundException("EMAIL_NOT_FOUND");
        }

        return new EmployeeDetail(employee);
    }
}
