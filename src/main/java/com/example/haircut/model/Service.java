package com.example.haircut.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Service")
public class Service {
    @Id
    private String id;

    private String serviceID;
    private String serviceName;
    private double price;
    private boolean status;
    private String durationTime;

    public Service() {
    }

    public Service(String id, String serviceID, String serviceName, double price, boolean status, String durationTime) {
        this.id = id;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
        this.status = status;
        this.durationTime = durationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
}
