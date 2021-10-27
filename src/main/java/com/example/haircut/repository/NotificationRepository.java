package com.example.haircut.repository;

import com.example.haircut.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Notification findTopByOrderByIdDesc();
    Notification findNotificationByNotiID(String id);
    Optional<Notification> findNotificationByApptID(String id);
    List<Notification> findNotificationByCusEmail(String cusEmail);
    List<Notification> findNotificationByApptIDAndRead(String apptID, boolean status);
}
