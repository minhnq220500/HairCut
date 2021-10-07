package com.example.haircut.apicontroller;

import com.example.haircut.model.Customer;
import com.example.haircut.repository.CustomerRepository;
import com.example.haircut.utils.Email;
import com.example.haircut.utils.RandomCode;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            else {
                //mã hóa password rồi mới lưu vào db
                String hash = BCrypt.hashpw(customerCanAdd.getPassword(), BCrypt.gensalt(4));
                customerCanAdd.setPassword(hash);

                RandomCode randomCode=new RandomCode();
                String verifyCode=randomCode.verifyCode();
                customerCanAdd.setVerifyCode(verifyCode);

                //send email
                Email email=new Email();
                email.sendEmail(customerCanAdd.getCusEmail(),verifyCode);

                //lần đầu addNew, status sẽ là inactive
                customerCanAdd.setStatus("inactive");

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
                String status=customer.getStatus();
                if(!status.equals("active")){
                    //nếu chưa active thì chuyển sang trang nhập verify code
                    // nhập sai thì cho nhập lại
                    // nhập đúng thì quay lại trang login
                    return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
                }
                else{
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
            }
            else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
