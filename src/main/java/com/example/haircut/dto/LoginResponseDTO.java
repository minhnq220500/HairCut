package com.example.haircut.dto;

import java.util.Date;

public class LoginResponseDTO {
    private String empEmail;
    private String password;
    private String empName;
    private String roleID;
    private String phone;
    private String seatNum;
    private boolean status;
    private String scheduleID;
    private Date hireDate;
    private Date dismissDate;
    private String token;

    public LoginResponseDTO(String empEmail, String password, String empName, String roleID, String phone, String seatNum, boolean status, String scheduleID, Date hireDate, Date dismissDate, String token) {
        this.empEmail = empEmail;
        this.password = password;
        this.empName = empName;
        this.roleID = roleID;
        this.phone = phone;
        this.seatNum = seatNum;
        this.status = status;
        this.scheduleID = scheduleID;
        this.hireDate = hireDate;
        this.dismissDate = dismissDate;
        this.token = token;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getDismissDate() {
        return dismissDate;
    }

    public void setDismissDate(Date dismissDate) {
        this.dismissDate = dismissDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
