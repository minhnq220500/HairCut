package com.example.haircut.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Location")
public class Location {

    private String locationID;
    private String address;
    private String state;
    private String city;
    private String status;

    public Location() {
    }

    public Location(String locationID, String address, String state, String city, String status) {
        this.locationID = locationID;
        this.address = address;
        this.state = state;
        this.city = city;
        this.status = status;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
