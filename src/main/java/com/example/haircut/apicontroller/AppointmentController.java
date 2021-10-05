package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    // create
    // post
    // request body dùng khi tạo mới thông tin
    @PostMapping("/createAppointment")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointmentCanAdd){
        try {
            Appointment appointment = appointmentRepository.save(appointmentCanAdd);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    // hiện ra tất cả appointment
    // get
    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointment(){
        try {
            List<Appointment> listAppt = new ArrayList<>();
            appointmentRepository.findAll().forEach(listAppt::add);
            if (listAppt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listAppt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // tìm
    //lấy ra 1 appointment bằng apptID hoặc bằng cusEmail
    @GetMapping("/appointment")
    public ResponseEntity<Appointment> getAppointmentByAppointmentId(@RequestParam(required = false) String apptID,String cusEmail) {
        Optional<Appointment> appointmentCanTim=null;
    //optional để kiểm tra xem có dữ liệu trong
        if(apptID==null)
            appointmentCanTim=appointmentRepository.findAppointmentByCusEmail(cusEmail);
        else
            appointmentCanTim=appointmentRepository.findAppointmentByApptID(apptID);
        if (appointmentCanTim.isPresent()) {
            return new ResponseEntity<>(appointmentCanTim.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //lấy ra appointment bằng customer email
//    @GetMapping("/appointmentCustomerEmail")
//    public ResponseEntity<Appointment> getAppointmentByCustomerEmail(@RequestParam(required = false) String cusEmail) {
//        Optional<Appointment> appointmentCanTim = appointmentRepository.findAppointmentByCusEmail(cusEmail);
//        //optional để kiểm tra xem có dữ liệu trong
//        if (appointmentCanTim.isPresent()) {
//            return new ResponseEntity<>(appointmentCanTim.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    //update
    //khi sử dụng request body thì phải cung cấp cho nó đủ thông tin của cái object java đó
    @PutMapping("/appointment")
    public ResponseEntity<Appointment> updateAppointment(@RequestParam String apptID, String status){
        Optional<Appointment> appointmentCanUpdateData=appointmentRepository.findAppointmentByApptID(apptID);
        // xem thử nó có trong database không
        if (appointmentCanUpdateData.isPresent()) {
            Appointment appointmentSeLuuVaoDatabase=appointmentCanUpdateData.get();// lấy data của cái trên
            appointmentSeLuuVaoDatabase.setStatus(status);
            return new ResponseEntity<>(appointmentRepository.save(appointmentSeLuuVaoDatabase), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
