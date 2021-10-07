package com.example.haircut.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customer")
public class Customer {

//    @Id
    //xác định thuộc tính hiện tại là ID trong bảng CSDL.
// @GeneratedValue
// xác định kiểu sinh khóa chính, ở đây là AUTO_INCREMENT
//    private String id;
    private String cusEmail;
    private String password;
    private String cusName;
    private String phone;
    private String status;
    private String verifyCode;

    public Customer() {
    }

    public Customer(String cusEmail, String password, String cusName, String phone, String status, String verifyCode) {
        this.cusEmail = cusEmail;
        this.password = password;
        this.cusName = cusName;
        this.phone = phone;
        this.status = status;
        this.verifyCode = verifyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

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

}
