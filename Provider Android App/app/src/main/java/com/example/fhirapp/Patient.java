package com.example.fhirapp;

class Patient {
    private String patientName;
    private String dob;
    private String gender;
    private String maritalStatus;
    private String status;
    private String id;
    private String htmlContent;

    public Patient(String patientName, String dob, String gender, String maritalStatus, String status, String id, String htmlContent) {
        this.patientName = patientName;
        this.dob = dob;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.status = status;
        this.id = id;
        this.htmlContent = htmlContent;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
