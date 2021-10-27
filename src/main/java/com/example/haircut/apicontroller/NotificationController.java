package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Notification;
import com.example.haircut.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/getNotiByCusEmail")
    public ResponseEntity<List<Notification>> getAllNotiByCusEmail(String cusEmail) {
        try {
            List<Notification> listNoti = new ArrayList<>();
            notificationRepository.findNotificationByCusEmailAndRead(cusEmail, false).forEach(listNoti::add);
            if (listNoti.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listNoti, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
