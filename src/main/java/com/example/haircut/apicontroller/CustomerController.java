package com.example.haircut.apicontroller;

import com.example.haircut.dto.LoginCustomerDTO;
import com.example.haircut.dto.LoginResponseDTO;
import com.example.haircut.model.Customer;
import com.example.haircut.repository.CustomerRepository;
import com.example.haircut.security.jwt.JWTConfig;
import com.example.haircut.utils.Email;
import com.example.haircut.utils.RandomCode;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
//import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.Jwts;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    private AuthenticationManager authenticationManager;
    private JWTConfig jwtConfig;
    private PasswordEncoder passwordEncoder;
    private SecretKey secretKey;

    @Autowired
    public CustomerController(AuthenticationManager authenticationManager, JWTConfig jwtConfig,
                              PasswordEncoder passwordEncoder, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<Customer> addNewCustomer(@RequestBody Customer customerCanAdd) {
        try {
            // check xem email có hay chưa
            Customer customer = customerRepository.findCustomerByCusEmail(customerCanAdd.getCusEmail());
            if (customer != null) {
                return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            } else {
                // mã hóa password rồi mới lưu vào db
                String hash = BCrypt.hashpw(customerCanAdd.getPassword(), BCrypt.gensalt(4));
                customerCanAdd.setPassword(hash);

                RandomCode randomCode = new RandomCode();
                String verifyCode = randomCode.verifyCode();
                customerCanAdd.setVerifyCode(verifyCode);

                try {
                    // send email
                    Email email = new Email();
                    email.sendEmail(customerCanAdd.getCusEmail(), verifyCode);
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
                }

                // lần đầu addNew, status sẽ là inactive
                customerCanAdd.setStatus("inactive");

                Customer customerSeLuuVaoDatabase = customerRepository.save(customerCanAdd);
                return new ResponseEntity<>(customerSeLuuVaoDatabase, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 500
        }
    }

    // nếu login với status inactive thì front sẽ chuyển sang trang nhập verifyCode
    // rồi gọi api của checkCode để kiểm tra code của customer nhập có đúng không,
    // nếu sai thì cho nhập lại
    // nhập đúng rồi thì gọi api của updateCustomerStatus để chuyển status thành
    // active
    @GetMapping("/checkCode")
    public ResponseEntity<Customer> checkStatus(@RequestParam String cusEmail, String code) {
        try {
            Customer customer = customerRepository.findCustomerByCusEmail(cusEmail);

            if (customer != null) {
                String verifyCode = customer.getVerifyCode();
                if (verifyCode.equals(code)) {
                    customer.setStatus("active");
                    return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestParam String cusEmail, String status) {
        try {
            Customer customer = customerRepository.findCustomerByCusEmail(cusEmail);
            if (customer != null) {
                customer.setStatus(status);
                return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 500
        }
    }

    @PutMapping("/updateCustomerPassword")
    public ResponseEntity<Customer> updateCustomerPassword(@RequestParam String cusEmail, String password, String newPassword) {
        try {
            Customer customer = customerRepository.findCustomerByCusEmail(cusEmail);
            if (customer != null) {
                String cusPassword=customer.getPassword();
                //so sánh 2 password
                boolean valuate = BCrypt.checkpw(password, cusPassword);

                //nếu check trong database giống mật khẩu thì làm bước tiếp theo
                if (valuate == true) {
                    // mã hóa password mới rồi mới lưu vào db
                    String hash = BCrypt.hashpw(newPassword, BCrypt.gensalt(4));
                    customer.setPassword(hash);

                    return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 500
        }
    }

    //quên password

    @PostMapping("/forgetPassword")
    public ResponseEntity<Customer> sendEmail(@RequestParam String cusEmail) {
        try {

            Customer customer = customerRepository.findCustomerByCusEmail(cusEmail);
            // check xem email có hay chưa
            if (customer == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {

                RandomCode randomCode = new RandomCode();
                String verifyCode = randomCode.verifyCode();
                customer.setVerifyCode(verifyCode);

                try {
                    // send email
                    Email email = new Email();
                    email.sendEmail(customer.getCusEmail(), verifyCode);
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
                }

                Customer customerSeLuuVaoDatabase = customerRepository.save(customer);
                return new ResponseEntity<>(customerSeLuuVaoDatabase, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 500
        }
    }

    // login
    @PostMapping("/customerLogin")
    public ResponseEntity<LoginCustomerDTO> login(@RequestParam String cusEmail, String password) {
        Customer customerCanDangNhap = customerRepository.findCustomerByCusEmail(cusEmail);

        if (customerCanDangNhap != null) {
            String status = customerCanDangNhap.getStatus();
            if (!status.equals("active")) {
                // nếu chưa active thì chuyển sang trang nhập verify code
                // nhập sai thì cho nhập lại
                // nhập đúng thì cập nhật status = active rồi quay lại trang login
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
            } else {
                Authentication authentication = new UsernamePasswordAuthenticationToken(cusEmail, password);

                Authentication authenticate = authenticationManager.authenticate(authentication);

                if (authenticate.isAuthenticated()) {
                    String token = Jwts.builder().setSubject(authentication.getName())
                            .claim("authorities", authenticate.getAuthorities()).setIssuedAt(new Date())
                            .setExpiration(
                                    java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                            .signWith(secretKey).compact();
                    // nhớ secret key
                    Customer customer = customerRepository.findCustomerByCusEmail(cusEmail);

                    LoginCustomerDTO loginResponse = new LoginCustomerDTO(customer.getCusEmail(), customer.getPassword(),
                            customer.getCusName(), customer.getPhone(), customer.getStatus(), customer.getVerifyCode(), "Bearer " + token);

                    return ResponseEntity.ok().body(loginResponse);

                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
