package com.example.haircut.repository;

import com.example.haircut.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    Optional<Customer> findCustomerByCusEmail(String cusEmail);
    Optional<Customer> findCustomerByCusEmailAndPassword(String cusEmail, String passworrd);

}
