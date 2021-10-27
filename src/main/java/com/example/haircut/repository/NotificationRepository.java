package com.example.haircut.repository;

import com.example.haircut.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Notification findTopByOrderByIdDesc();
    Notification findNotificationByNotiID(String id);
    List<Notification> findNotificationByApptIDAndRead(String id, boolean status);
    Notification findNotificationByApptID(String id);
    List<Notification> findNotificationByCusEmailAndRead(String cusEmail, boolean status);

}
