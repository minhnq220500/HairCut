package com.example.haircut.apicontroller;

import com.example.haircut.model.Customer;
import com.example.haircut.model.Employee;
import com.example.haircut.repository.EmployeeRepository;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    //login
    // get
    //lúc login có cần chọn role admin hay staff không, hay là để chung 1 chỗ?

    @PostMapping("/empLogin")
    public ResponseEntity<Employee> login(@RequestParam String empEmail, String password){
        try {
            //mã hóa psssword
//            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(4));
            Optional<Employee> employeeCanDangNhap=employeeRepository.findEmployeeByEmpEmail(empEmail);

            if (employeeCanDangNhap.isPresent()) {
                Employee employee=employeeCanDangNhap.get();
                String empPassword=employee.getPassword();
                //check xem 2 cái đã mã hóa có giống nhau hay không
                boolean valuate = BCrypt.checkpw(password, empPassword);
                if (valuate==true){
                    return new ResponseEntity<>(employee,HttpStatus.OK);
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

    @PostMapping("/addNewEmployee")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee){
        try {
            //mã hóa psssword
//            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(4));
            Optional<Employee> employeeData=employeeRepository.findEmployeeByEmpEmail(employee.getEmpEmail());

            if (employeeData.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            else{
                //mã hóa password rồi mới lưu vào db
                String hash = BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt(4));
                employee.setPassword(hash);

                Employee _employee=employeeRepository.save(employee);

                return new ResponseEntity<>(_employee,HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        try {
            List<Employee> listEmp=new ArrayList<>();
            employeeRepository.findAll().forEach(listEmp::add);
            if(listEmp.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listEmp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
