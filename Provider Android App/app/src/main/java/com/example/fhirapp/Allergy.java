package com.example.fhirapp;

class Allergy {

    private String allergySubstance;
    private String status;
    private String recordedDate;
    private String htmlContent;

    public Allergy(String allergySubstance, String status, String recordedDate, String htmlContent) {
        this.allergySubstance = allergySubstance;
        this.status = status;
        this.recordedDate = recordedDate;
        this.htmlContent = htmlContent;
    }

    public String getAllergySubstance() {
        return allergySubstance;
    }

    public void setAllergySubstance(String allergySubstance) {
        this.allergySubstance = allergySubstance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(String recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
