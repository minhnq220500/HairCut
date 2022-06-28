package com.example.haircut.model;

public class AppointmentCustom {

    private Appointment appointment;
    private Feedback feedback;

    public AppointmentCustom() {
    }

    public AppointmentCustom(Appointment appointment, Feedback feedback) {
        this.appointment = appointment;
        this.feedback = feedback;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
