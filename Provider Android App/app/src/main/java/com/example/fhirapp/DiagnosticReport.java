package com.example.fhirapp;

class DiagnosticReport {

    private String reportType;
    private String encounterNumber;
    private String issuedDate;
    private String htmlContent;
    private String diagnosticPdfUrl;

    public String getDiagnosticPdfUrl() {
        return diagnosticPdfUrl;
    }

    public void setDiagnosticPdfUrl(String diagnosticPdfUrl) {
        this.diagnosticPdfUrl = diagnosticPdfUrl;
    }

    public DiagnosticReport(String reportType, String encounterNumber, String issuedDate, String htmlContent, String diagnosticPdfUrl) {
        this.reportType = reportType;
        this.encounterNumber = encounterNumber;
        this.issuedDate = issuedDate;
        this.htmlContent = htmlContent;
        this.diagnosticPdfUrl = diagnosticPdfUrl;

    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getEncounterNumber() {
        return encounterNumber;
    }

    public void setEncounterNumber(String encounterNumber) {
        this.encounterNumber = encounterNumber;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
