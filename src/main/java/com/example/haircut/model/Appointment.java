package com.example.haircut.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Appointment")
public class Appointment {

    @Id
//xác định thuộc tính hiện tại là ID trong bảng CSDL.
    private String id;
    private String apptID;
    private String status;
    private Date date;
    private String description;
    private String cusEmail;
    private double totalPrice;
    private int totalDuration;
    private Date startTime;

    public Appointment() {
    }

    public Appointment(String apptID, String status, Date date, String description, String cusEmail, double totalPrice, int totalDuration, Date startTime) {
        this.apptID = apptID;
        this.status = status;
        this.date = date;
        this.description = description;
        this.cusEmail = cusEmail;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
        this.startTime = startTime;
    }

    public String getApptID() {
        return apptID;
    }

    public void setApptID(String apptID) {
        this.apptID = apptID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

}
