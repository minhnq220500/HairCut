package com.example.haircut.apicontroller;

import com.example.haircut.model.*;
import com.example.haircut.repository.AppointmentRepository;
import com.example.haircut.repository.FeedbackRepository;
import com.example.haircut.repository.NotificationRepository;
import com.example.haircut.repository.ServiceRepository;
import com.example.haircut.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.spi.ServiceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    // create
    // post
    // request body dùng khi tạo mới thông tin ok
    @PostMapping("/createAppointment")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointmentCanAdd) {
        try {
            //Appointment appointmentLast = appointmentRepository.findAll(Sort.by(Sort.Direction.DESC, "apptID")).get(0);
            Appointment appointmentLast = appointmentRepository.findTopByOrderByIdDesc();
            String currentMaxId = appointmentLast.getApptID();
            String newID = new MyUtil().autoIncrementId(currentMaxId);
            appointmentCanAdd.setApptID(newID);

            //tạo ra appoiment, cùng lúc tạo ra notification cho nó
            try{
                Notification notification=notificationRepository.findTopByOrderByIdDesc();
                String currentMaxNotiId = notification.getNotiID();
                String newNotiID = new MyUtil().autoIncrementId(currentMaxNotiId);

                String currentStatus="ON PROCESS";
                String newStatus="ON PROCESS";
                boolean isRead=false;
                String cusEmail=appointmentCanAdd.getCusEmail();
                String apptID=newID;

                Notification notification1=new Notification(newNotiID,currentStatus,newStatus,isRead,cusEmail,apptID);
                notificationRepository.save(notification1);

            }catch (Exception e){
                System.out.println(e);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);// 500
            }

            appointmentRepository.save(appointmentCanAdd);

            return new ResponseEntity<>(appointmentCanAdd, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 500
        }
    }

    // hiện ra tất cả appointment
    // dung de phan quyen bang role
//    @PreAuthorize("hasRole('st')")

    // @PreAuthorize("hasAnyRole('Donator'))
    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointment() {
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
    // lấy ra 1 appointment bằng apptID
    @GetMapping("/appointmentApptID")
    public ResponseEntity<Appointment> getAppointmentByAppointmentId(@RequestParam String apptID) {
        Optional<Appointment> appointmentCanTim = appointmentRepository.findAppointmentByApptID(apptID);
        if (appointmentCanTim!=null) {
            return new ResponseEntity<>(appointmentCanTim.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   //  lấy ra appointment bằng customer email
     @GetMapping("/appointmentCusEmail")
     public ResponseEntity<List<Appointment>> getAppointmentByCustomerEmail(@RequestParam String cusEmail) {
         try {
             List<Appointment> listAppt = new ArrayList<>();
             appointmentRepository.findAppointmentByCusEmail(cusEmail).forEach(listAppt::add);
             if (listAppt.isEmpty()) {
                 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
             }
             return new ResponseEntity<>(listAppt, HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    // update
    // khi sử dụng request body thì phải cung cấp cho nó đủ thông tin của cái object
    // java đó
    @PutMapping("/updateAppointmentStatus")
    public ResponseEntity<Appointment> updateAppointment(@RequestParam String apptID, String status, String empEmail, String description) {
        Optional<Appointment> appointmentCanUpdateData = appointmentRepository.findAppointmentByApptID(apptID);

        Optional<Notification> noti=notificationRepository.findNotificationByApptID(apptID);
        // xem thử nó có trong database không
        if (appointmentCanUpdateData.isPresent() && noti.isPresent()) {
            Appointment appointmentSeLuuVaoDatabase = appointmentCanUpdateData.get();// lấy data của cái trên
            appointmentSeLuuVaoDatabase.setStatus(status);
            appointmentSeLuuVaoDatabase.setEmpEmail(empEmail);
            appointmentSeLuuVaoDatabase.setDescription(description);

            try{
                Notification notification_=noti.get();
                notification_.setNewStatus(status);
                notificationRepository.save(notification_);
            }catch (Exception e){
                System.out.println(e);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(appointmentRepository.save(appointmentSeLuuVaoDatabase), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointmentCustomApptID")
    public ResponseEntity<List<AppointmentCustom>> getAppointmentCustomByAppointmentId(@RequestParam String cusEmail) {
        List<Appointment> appointments = new ArrayList<>();
        List<AppointmentCustom> appointmentCustoms = new ArrayList<>();
        appointments = appointmentRepository.findAppointmentByCusEmail(cusEmail);
        if (appointments.size() != 0) {
            for(Appointment a : appointments){
                AppointmentCustom appointmentCustom = new AppointmentCustom();
                appointmentCustom.setAppointment(a);
                Optional<Feedback> feedback = feedbackRepository.findFeedbackByCusEmailAndApptID(cusEmail, a.getApptID());
                if(feedback.isPresent()){
                    appointmentCustom.setFeedback(feedback.get());
                }
                appointmentCustoms.add(appointmentCustom);

            }

            return new ResponseEntity<>(appointmentCustoms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
