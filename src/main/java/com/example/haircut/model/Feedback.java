package com.example.haircut.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Feedback")
public class Feedback {
    private String feedbackID;
    private String rating;
    private String comment;
    private String cusEmail;
    private String apptID;

    public Feedback() {
    }

    public Feedback(String feedbackID, String rating, String comment, String cusEmail, String apptID) {
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.comment = comment;
        this.cusEmail = cusEmail;
        this.apptID = apptID;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getApptID() {
        return apptID;
    }

    public void setApptID(String apptID) {
        this.apptID = apptID;
    }
}
