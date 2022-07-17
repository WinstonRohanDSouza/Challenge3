package com.example.fhirapp;

import android.net.Uri;

import io.realm.RealmObject;

public class FileNotes extends RealmObject {

    String title;
    String description;
    String pdfAddress, docType;
    long createdtime;
    byte[] diagnosticPdfReport;

    public byte[] getDiagnosticPdfReport() {
        return diagnosticPdfReport;
    }

    public void setDiagnosticPdfReport(byte[] diagnosticPdfReport) {
        this.diagnosticPdfReport = diagnosticPdfReport;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPdfAddress() {
        return pdfAddress;
    }

    public void setPdfAddress(String pdfAddress) {
        this.pdfAddress = pdfAddress;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }
}
