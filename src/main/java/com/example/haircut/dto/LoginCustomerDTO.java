package com.example.haircut.dto;

import com.example.haircut.model.Customer;

public class LoginCustomerDTO {
    private String cusEmail;
    private String password;
    private String cusName;
    private String phone;
    private String status;
    private String verifyCode;
    private String token;

    public LoginCustomerDTO() {
    }

    public LoginCustomerDTO(String cusEmail, String password, String cusName, String phone, String status,
            String verifyCode, String token) {
        this.cusEmail = cusEmail;
        this.password = password;
        this.cusName = cusName;
        this.phone = phone;
        this.status = status;
        this.verifyCode = verifyCode;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    // public String getId() {
    // return id;
    // }
    //
    // public void setId(String id) {
    // this.id = id;
    // }

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
