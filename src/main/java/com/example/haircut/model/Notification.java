package com.example.haircut.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Notification")
public class Notification {
    @Id
    private String id;
    private String notiID;
    private String currentStatus;
    private String newStatus;
    private boolean isRead;
    private String cusEmail;
    private String apptID;

    public Notification(String notiID, String currentStatus, String newStatus, boolean isRead, String cusEmail, String apptID) {
        this.notiID = notiID;
        this.currentStatus = currentStatus;
        this.newStatus = newStatus;
        this.isRead = isRead;
        this.cusEmail = cusEmail;
        this.apptID = apptID;
    }

    public String getNotiID() {
        return notiID;
    }

    public void setNotiID(String notiID) {
        this.notiID = notiID;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getApptID() {
        return apptID;
    }

    public void setApptID(String apptID) {
        this.apptID = apptID;
    }
}
