package com.example.haircut.apicontroller;

import com.example.haircut.dto.LoginResponseDTO;
import com.example.haircut.model.Customer;
import com.example.haircut.model.Employee;
import com.example.haircut.model.Schedule;
import com.example.haircut.repository.EmployeeRepository;
import com.example.haircut.security.jwt.JWTConfig;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private AuthenticationManager authenticationManager;
    private JWTConfig jwtConfig;
    private PasswordEncoder passwordEncoder;
    private SecretKey secretKey;

    @Autowired
    public EmployeeController(AuthenticationManager authenticationManager, JWTConfig jwtConfig,
            PasswordEncoder passwordEncoder, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    // login
    // get
    // lúc login có cần chọn role admin hay staff không, hay là để chung 1 chỗ?

    @PostMapping("/empLogin")
    public ResponseEntity<LoginResponseDTO> login(@RequestParam String empEmail, String password) {
        // mã hóa psssword
        // String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(4));

        Authentication authentication = new UsernamePasswordAuthenticationToken(empEmail, password);

        Authentication authenticate = authenticationManager.authenticate(authentication);

        Employee employeeCanDangNhap = employeeRepository.findEmployeeByEmpEmail(empEmail);

        if (authenticate.isAuthenticated()) {
            String token = Jwts.builder().setSubject(authentication.getName())
                    .claim("authorities", authenticate.getAuthorities()).setIssuedAt(new Date())
                    .setExpiration(
                            java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                    .signWith(secretKey).compact();
            // nhớ secret key
            Employee employee = employeeRepository.findEmployeeByEmpEmail(empEmail);

//            LoginResponseDTO loginResponse = new LoginResponseDTO(employee.getEmpEmail(), employee.getEmpName(),employee.getPassword(),
//                    employee.getRoleID(), employee.getPhone(), employee.getSeatNum(), employee.isStatus(),
//                    employee.getScheduleID(), employee.getHireDate(), employee.getDismissDate(),"Bearer " + token);
            LoginResponseDTO loginResponse= new LoginResponseDTO(employee.getEmpEmail(),employee.getPassword(),employee.getEmpName(),
                    employee.getRoleID(),employee.getPhone(),employee.getSeatNum(),employee.isStatus(),employee.getScheduleID(),
                    employee.getHireDate(),employee.getDismissDate(),"Bearer " + token);

            return ResponseEntity.ok().body(loginResponse);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // if (employeeCanDangNhap!=null) {
        // String empPassword=employeeCanDangNhap.getPassword();
        // //check xem 2 cái đã mã hóa có giống nhau hay không
        // boolean valuate = BCrypt.checkpw(password, empPassword);
        // if (valuate==true){
        // return new ResponseEntity<>(employeeCanDangNhap,HttpStatus.OK);
        // }
        // else {
        // return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        // }
        // }
        // else{
        // return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        // }

    }

    @PostMapping("/addNewEmployee")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        try {
            // mã hóa psssword
            // String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(4));
            Employee employeeData = employeeRepository.findEmployeeByEmpEmail(employee.getEmpEmail());

            if (employeeData != null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                // mã hóa password rồi mới lưu vào db
                String hash = BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt(4));
                employee.setPassword(hash);

                Employee _employee = employeeRepository.save(employee);

                return new ResponseEntity<>(_employee, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        try {
            List<Employee> listEmp = new ArrayList<>();
            employeeRepository.findAll().forEach(listEmp::add);
            if (listEmp.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listEmp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/getEmployeeStatusTrue")
    public ResponseEntity<List<Employee>> getAllEmployeeStatusTrue(String roleID) {
        try {
            List<Employee> listEmp = new ArrayList<>();
            employeeRepository.findEmployeeByRoleID(roleID).forEach(listEmp::add);
            if (listEmp.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                List<Employee> listStatusTrue=new ArrayList<>();
                for(Employee employee:listEmp){
                    if(employee.isStatus()==true){
                        listStatusTrue.add(employee);
                    }
                }
                return new ResponseEntity<>(listStatusTrue,HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employeesBySchedule")
    public ResponseEntity<List<Employee>> getAllEmployeeBySchedule(@RequestBody Schedule schedule) {
        try {
            List<Employee> listEmp = new ArrayList<>();
            employeeRepository.findByScheduleID(schedule.getScheduleID()).forEach(listEmp::add);
            if (listEmp.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(listEmp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateEmployeeInfo")
    public ResponseEntity<Employee> updateEmployeeInfo(@RequestBody Employee employee){
        try{
            Employee employee1=employeeRepository.findEmployeeByEmpEmail(employee.getEmpEmail());
            if(employee1!=null){
                employee1.setEmpName(employee.getEmpName());
                employee1.setPhone(employee.getPhone());
                employee1.setSeatNum(employee.getSeatNum());
                employee1.setStatus(employee.isStatus());

                employee1.setScheduleID(employee.getScheduleID());

                employee1.setHireDate(employee.getHireDate());
                employee1.setDismissDate(employee.getDismissDate());

                return new ResponseEntity<>(employeeRepository.save(employee1), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            System.out.println(e.toString().toUpperCase());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //cập nhật schedule của list Employee
    @PutMapping("/updateEmployeeSchedule")
    public ResponseEntity<Employee> updateEmployeeSchedule(@RequestParam String scheduleID, List<String> listEmployeeEmail){
        try{
            if(listEmployeeEmail.size()==0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                for (String employeeEmail : listEmployeeEmail) {
                    Employee employee=employeeRepository.findEmployeeByEmpEmail(employeeEmail);
                    employee.setScheduleID(scheduleID);
                    employeeRepository.save(employee);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (Exception e){
            System.out.println(e.toString().toUpperCase());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
