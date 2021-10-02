package com.example.haircut.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Service {
    private String serviceID;
    private String serviceName;
    private double price;
    private String status;
    private String durationTime;

    public Service() {
    }

    public Service(String serviceID, String serviceName, double price, String status, String durationTime) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
        this.status = status;
        this.durationTime = durationTime;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
}
