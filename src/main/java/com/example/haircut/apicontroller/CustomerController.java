package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Customer;
import com.example.haircut.repository.CustomerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/addNewCustomer")
    public ResponseEntity<Customer> addNewCustomer(@RequestBody Customer customerCanAdd){
        try {
            //check xem email có hay chưa
            Optional<Customer> customer = customerRepository.findCustomerByCusEmail(customerCanAdd.getCusEmail());
            if(customer.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            }
            else {
                //mã hóa password rồi mới lưu vào db
                String hash = BCrypt.hashpw(customerCanAdd.getPassword(), BCrypt.gensalt(4));
                customerCanAdd.setPassword(hash);

                Customer customerSeLuuVaoDatabase = customerRepository.save(customerCanAdd);
                return new ResponseEntity<>(customerSeLuuVaoDatabase, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    //login
    // get
    @GetMapping("/customerLogin")
    public ResponseEntity<Customer> login(@RequestParam String cusEmail, String password){
        try {
            Optional<Customer> customerCanDangNhap=customerRepository.findCustomerByCusEmail(cusEmail);

            if (customerCanDangNhap.isPresent()) {
                Customer customer=customerCanDangNhap.get();
                String cusPassword=customer.getPassword();
                //check xem 2 cái đã mã hóa có giống nhau hay không
                boolean valuate = BCrypt.checkpw(password, cusPassword);
                if (valuate==true){
                    return new ResponseEntity<>( customer,HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
