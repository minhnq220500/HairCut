package com.example.haircut.model;

public class ServiceCount {
    private String serviceID;
    private int count;

    public ServiceCount(String serviceID, int count) {
        this.serviceID = serviceID;
        this.count = count;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
