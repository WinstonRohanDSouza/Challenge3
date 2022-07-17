package com.example.fhirapp;

class Medications {

    private String vaccineName;
    private String date;
    private String status;
    private String htmlContent;
    private String encounterNumber;

    public Medications(String vaccineName, String date, String status, String htmlContent, String encounterNumber) {
        this.vaccineName = vaccineName;
        this.date = date;
        this.status = status;
        this.htmlContent = htmlContent;
        this.encounterNumber = encounterNumber;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEncounterNumber() {
        return encounterNumber;
    }

    public void setEncounterNumber(String encounterNumber) {
        this.encounterNumber = encounterNumber;
    }
}
