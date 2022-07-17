package com.example.fhirapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

class PatientDocumentHandler {

    private static final String DOCUMENT_DIR_KEY = "PRAN_DOC_DIR";
    private static final String DOCUMENT_NAME_KEY = "PRAN_DOC_NAME";
    private static final String DOCUMENT_DATE_KEY = "PRAN_DOC_DATE";
    private static final String DOCUMENT_COUNT_KEY = "PRAN_DOC_COUNT";
    private List<String> documentDir;
    private List<String> documentName;
    private List<String> documentDate;
    private int docCount;
    private Context context;
    private SharedPreferences sharedPreferences;

    public PatientDocumentHandler(Context context) {
        this.context = context;
        documentDir = new ArrayList<>();
        documentName = new ArrayList<>();
        documentDate = new ArrayList<>();

        sharedPreferences = context.getSharedPreferences("PRAN", MODE_PRIVATE);
        docCount = sharedPreferences.getInt(DOCUMENT_COUNT_KEY, 0);

        for(int i=0; i<docCount; i++){
            documentDir.add(sharedPreferences.getString(DOCUMENT_DIR_KEY + Integer.toString(i), ""));
            documentName.add(sharedPreferences.getString(DOCUMENT_NAME_KEY + Integer.toString(i), ""));
            documentDate.add(sharedPreferences.getString(DOCUMENT_DATE_KEY + Integer.toString(i), ""));
        }
    }

    private void updateList(){
        if(docCount != sharedPreferences.getInt(DOCUMENT_COUNT_KEY, 0) || documentDir.size()!=docCount || documentName.size()!=docCount || documentDate.size()!=docCount){
            docCount = sharedPreferences.getInt(DOCUMENT_COUNT_KEY, 0);

            documentDir.clear();
            documentName.clear();

            for(int i=0; i<docCount; i++){
                documentDir.add(sharedPreferences.getString(DOCUMENT_DIR_KEY + Integer.toString(i), ""));
                documentName.add(sharedPreferences.getString(DOCUMENT_NAME_KEY + Integer.toString(i), ""));
                documentDate.add(sharedPreferences.getString(DOCUMENT_DATE_KEY + Integer.toString(i), ""));
            }
        }
    }

    public void addFile(String fileLocation, String fileName, String date){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DOCUMENT_DIR_KEY + Integer.toString(docCount), fileLocation);
        documentDir.add(fileLocation);
        editor.putString(DOCUMENT_NAME_KEY + Integer.toString(docCount), fileName);
        documentName.add(fileName);
        editor.putString(DOCUMENT_DATE_KEY + Integer.toString(docCount), date);
        documentName.add(date);
        editor.putInt(DOCUMENT_COUNT_KEY, ++docCount);

        editor.commit();

        updateList();
    }

    public void deleteFile(int index){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(DOCUMENT_DIR_KEY + Integer.toString(index));
        documentDir.remove(index);
        editor.remove(DOCUMENT_NAME_KEY + Integer.toString(index));
        documentName.remove(index);
        editor.remove(DOCUMENT_DATE_KEY + Integer.toString(index));
        documentDate.remove(index);
        editor.putInt(DOCUMENT_COUNT_KEY, --docCount);

        editor.commit();

        updateList();
    }

    public List<String> getDocumentDir() {
        return documentDir;
    }

    public List<String> getDocumentName() {
        return documentName;
    }

    public List<String> getDocumentDate() {
        return documentDate;
    }

    public int getDocCount() {
        return docCount;
    }
}
