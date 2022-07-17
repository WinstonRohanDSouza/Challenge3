package com.example.fhirapp;

public class Appointment {

    private String appointmentReason;
    private String appointmentStatus;
    private String appointmentDate;
    private  String appointmentHtmlContent;


    public Appointment(String appointmentReason, String appointmentStatus, String appointmentDate, String appointmentHtmlContent) {
        this.appointmentReason = appointmentReason;
        this.appointmentStatus = appointmentStatus;
        this.appointmentDate = appointmentDate;
        this.appointmentHtmlContent = appointmentHtmlContent;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentHtmlContent() {
        return appointmentHtmlContent;
    }

    public void setAppointmentHtmlContent(String appointmentHtmlContent) {
        this.appointmentHtmlContent = appointmentHtmlContent;
    }


}
