package com.example.haircut.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AppointmentDetail")
public class AppointmentDetail {
    private String detailID;
    private String apptID;
    private String cusEmail;
    private String serviceID;
    private double price;

    public AppointmentDetail() {
    }

    public AppointmentDetail(String detailID, String apptID, String cusEmail, String serviceID, double price) {
        this.detailID = detailID;
        this.apptID = apptID;
        this.cusEmail = cusEmail;
        this.serviceID = serviceID;
        this.price = price;
    }

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public String getApptID() {
        return apptID;
    }

    public void setApptID(String apptID) {
        this.apptID = apptID;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
