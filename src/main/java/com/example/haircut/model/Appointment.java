package com.example.haircut.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Appointment")
public class Appointment {

    @Id
//xác định thuộc tính hiện tại là ID trong bảng CSDL.
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
// xác định kiểu sinh khóa chính, ở đây là AUTO_INCREMENT
    private String id;

    private String apptID;
    private String status;
    private Date date;
    private String description;
    private String cusEmail;
    private double totalPrice;
    private Date startTime;
    private Date endTime;

    public Appointment() {
    }

    public Appointment(String apptID, String status, Date date, String description, String cusEmail, double totalPrice, Date startTime, Date endTime) {
        this.apptID = apptID;
        this.status = status;
        this.date = date;
        this.description = description;
        this.cusEmail = cusEmail;
        this.totalPrice = totalPrice;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "apptID='" + apptID + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", totalPrice=" + totalPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
