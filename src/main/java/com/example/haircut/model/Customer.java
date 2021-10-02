package com.example.haircut.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customer")
public class Customer {

    @Id
    //xác định thuộc tính hiện tại là ID trong bảng CSDL.
// @GeneratedValue
// xác định kiểu sinh khóa chính, ở đây là AUTO_INCREMENT
    private String id;
    private String cusEmail;
    private String password;
    private String cusName;
    private String phone;
    private boolean status;

    public Customer() {
    }

    public Customer(String cusEmail, String password, String cusName, String phone, boolean status) {
        this.cusEmail = cusEmail;
        this.password = password;
        this.cusName = cusName;
        this.phone = phone;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
