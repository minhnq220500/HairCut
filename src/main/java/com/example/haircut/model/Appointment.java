package com.example.haircut.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

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
    private String empEmail;
    private List<Service> listService;
    private Date createDate;
    private String discountCode;

    public Appointment() {
    }

    public Appointment(String id, String apptID, String status, Date date, String description, String cusEmail, double totalPrice, int totalDuration, Date startTime, String empEmail, List<Service> listService, Date createDate, String discountCode) {
        this.id = id;
        this.apptID = apptID;
        this.status = status;
        this.date = date;
        this.description = description;
        this.cusEmail = cusEmail;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
        this.startTime = startTime;
        this.empEmail = empEmail;
        this.listService = listService;
        this.createDate = createDate;
        this.discountCode = discountCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public List<Service> getListService() {
        return listService;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}
