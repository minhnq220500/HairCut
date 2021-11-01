package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Notification;
import com.example.haircut.repository.NotificationRepository;
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
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/getNotiByCusEmail")
    public ResponseEntity<List<Notification>> getAllNotiByCusEmail(String cusEmail) {
        try {
            List<Notification> listNoti = new ArrayList<>();
            notificationRepository.findNotificationByCusEmail(cusEmail).forEach(listNoti::add);
            if (listNoti.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                List<Notification> listNotiFalse=new ArrayList<>();
                for (Notification noti:listNoti) {
                    if(noti.isRead()==false && noti.getCurrentStatus()=="ACCEPT" || noti.getCurrentStatus()=="CANCEL BY ADMIN"){
                        listNotiFalse.add(noti);
                    }
                }
                return new ResponseEntity<>(listNotiFalse, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update isRead thành true
    @PutMapping("/updateNotiIsReadStatus")
    public ResponseEntity<Notification> updateAppointment(@RequestParam String notiID) {
        Notification notification=notificationRepository.findNotificationByNotiID(notiID);
        // xem thử nó có trong database không
        if (notification!=null) {
            notification.setRead(true);
            notificationRepository.save(notification);
            return new ResponseEntity<>(notificationRepository.save(notification), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
