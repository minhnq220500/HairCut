package com.example.haircut.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Schedule")
public class Schedule {
    private String scheduleID;
    private Date startTime;
    private Date endTime;

    public Schedule() {
    }

    public Schedule(String scheduleID, Date startTime, Date endTime) {
        this.scheduleID = scheduleID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
