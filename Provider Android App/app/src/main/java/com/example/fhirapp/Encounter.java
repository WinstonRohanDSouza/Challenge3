package com.example.fhirapp;

import android.text.Spanned;

class Encounter {

    private String fullURL;
    private String encounterId;
    private String htmlContent;
    private String date;
    private String patientName;
    private String cheifComplaint;

    public Encounter(String fullURL, String encounterId, String htmlContent, String date, String patientName, String cheifComplaint) {
        this.fullURL = fullURL;
        this.encounterId = encounterId;
        this.htmlContent = htmlContent;
        this.date = date;
        this.patientName = patientName;
        this.cheifComplaint = cheifComplaint;
    }

    public String getFullURL() {
        return fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterId) {
        this.encounterId = encounterId;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCheifComplaint() {
        return cheifComplaint;
    }

    public void setCheifComplaint(String cheifComplaint) {
        this.cheifComplaint = cheifComplaint;
    }

}
