package com.example.haircut.security.authentication;

import com.example.haircut.model.Customer;
import com.example.haircut.model.Employee;
import com.example.haircut.repository.CustomerRepository;
import com.example.haircut.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String customerEmail) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByCusEmail(customerEmail);

        if (customer == null) {
            throw new UsernameNotFoundException("EMAIL_NOT_FOUND");
        }

        return new CustomerDetail(customer);
    }
}
