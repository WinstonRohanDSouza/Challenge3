package com.example.fhirapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private List<Encounter> encountersList;
    private List<DiagnosticReport> diagnosticReportList;
    private List<Allergy> allergyList;
    private List<Medications> medicationsList;
    private List<Appointment> appointmentList;

    private String jsonData;
    private String type;

    //private PatientDetails temp;

    private List<String> title;
    private List<String> date;

    DrawerLayout drawerLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Intent intent = getIntent();
        jsonData = intent.getStringExtra("divTextEncounter");
        type = intent.getStringExtra("class");



        /*temp = new PatientDetails();
        encountersList = new ArrayList<>();
        encountersList = temp.getEncountersList();
        Log.e("ViewAllActivity", "encounterListSize: " + encountersList.size());*/

        initData();

        for(int i=0; i<title.size(); i++)
            Log.e("ViewAllActivity","titleValues: " + title.get(i));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_viewall);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(title,date,this,encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList, type,-1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }

    private void initData() {
        drawerLayout = findViewById(R.id.drawer_layout_viewall);
        textView = findViewById(R.id.type_textview_view_all);

        title = new ArrayList<>();
        date = new ArrayList<>();

        Log.e("Fetch_Type",type);

        if(type.equals("Encounter")){
            textView.setText("Encounter");
            parseEncounter(jsonData);
//            parseDiagnosticReport(jsonData);
//            parseAllergyIntolerance(jsonData);
//            parseMedications(jsonData);
//            parseAppointment(jsonData);
            for(int i=0; i<encountersList.size();i++){
                title.add(encountersList.get(i).getCheifComplaint());
                date.add(encountersList.get(i).getDate());
            }

            encounterRelations();

        }else if(type.equals("DiagnosticReport")){
            textView.setText("Diagnostic Report");
            parseDiagnosticReport(jsonData);
            for(int i=0; i<diagnosticReportList.size();i++){
                title.add(diagnosticReportList.get(i).getReportType());
                date.add(diagnosticReportList.get(i).getIssuedDate());
            }

        }else if(type.equals("Allergies")){
            textView.setText("Allergies");
            parseAllergyIntolerance(jsonData);
            for(int i=0; i<allergyList.size();i++){
                title.add(allergyList.get(i).getAllergySubstance());
                date.add(allergyList.get(i).getRecordedDate());
            }

        }else if(type.equals("Medications")){
            textView.setText("Medications");
            parseMedications(jsonData);
            for(int i=0; i<medicationsList.size();i++){
                title.add(medicationsList.get(i).getVaccineName());
                date.add(medicationsList.get(i).getDate());
            }
        }
        else if(type.equals("Appointment")){
            textView.setText("Appointment");
            parseAppointment(jsonData);
            for(int i=0; i<appointmentList.size();i++){
                title.add(appointmentList.get(i).getAppointmentReason());
                date.add(appointmentList.get(i).getAppointmentDate());
            }
        }
    }

    private void encounterRelations() {



    }

    public List<Encounter> parseEncounter(String responseBody){

        JSONObject jo;
        encountersList = new ArrayList<>();

        try {

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

            JSONObject test;
            String patientName, encounterNumber, issuedDate, cheifCompliant, htmlcontent, fullURl;
            ArrayList<String> arrayList = new ArrayList<>();

            for(int i=0; i<encounters.length(); i++){

                test = (JSONObject) encounters.get(i);
                patientName = ((JSONObject)((JSONObject) test.get("resource")).get("patient")).get("display").toString();
                encounterNumber = (((JSONObject) test.get("resource")).get("id")).toString();
                htmlcontent = ((JSONObject)((JSONObject) test.get("resource")).get("text")).get("div").toString();
                fullURl = test.get("fullUrl").toString();

                try{
                    cheifCompliant = ((JSONObject)((JSONArray)((JSONObject) test.get("resource")).get("reason")).get(0)).get("text").toString();
                    issuedDate = ((JSONObject)((JSONObject)((JSONArray)((JSONObject) test.get("resource")).get("participant")).get(0)).get("period")).get("start").toString();
                }catch(JSONException e){
                    continue;
                }

                String[] issuedDateSplit = issuedDate.split("T");

                Encounter temp = new Encounter(fullURl,encounterNumber,htmlcontent,"2020-07-29",patientName,cheifCompliant);
                encountersList.add(temp);

                arrayList.add(cheifCompliant + " (" + issuedDateSplit[0] + ")");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<encountersList.size(); i++){

            Log.e("ObjectTesting",encountersList.get(i).getEncounterId());

        }

        return encountersList;
    }

    public List<DiagnosticReport> parseDiagnosticReport(String responseBody){

        JSONObject jo;
        diagnosticReportList = new ArrayList<>();

        try {

            jo = new JSONObject(responseBody);

            JSONArray diagnostics = (JSONArray) jo.get("entry");

            JSONObject test;
            String reportType, encounterNumber, issuedDate, htmlcontent,diagnosticPdfUrl = null;
            ArrayList<String> arrayList = new ArrayList<>();

            for(int i=0; i<diagnostics.length(); i++){

                test = (JSONObject) diagnostics.get(i);
                encounterNumber = ((JSONObject)((JSONObject) test.get("resource")).get("encounter")).get("reference").toString();
                issuedDate = ((JSONObject) test.get("resource")).get("issued").toString();
                reportType = ((JSONObject)((JSONObject) test.get("resource")).get("code")).get("text").toString();
                htmlcontent = ((JSONObject)((JSONObject) test.get("resource")).get("text")).get("div").toString();

                try {
                    JSONArray ja = (JSONArray) ((JSONObject) test.get("resource")).getJSONArray("presentedForm");
                    Log.d("JSONARRAYpresentedForm", String.valueOf(ja));
                    for(int j=0;j< ja.length();j++){
                        JSONObject test2 = (JSONObject) ja.getJSONObject(j);
                        try {
                            if( (test2.getString("contentType")).equals("application/pdf")){
                                diagnosticPdfUrl = test2.getString("url");
                                Log.d("diagnosticPdfUrl",diagnosticPdfUrl);
                            }
                        } catch (Exception e) {
                            Log.d("IsItWorking","No");
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                String[] issuedDateSplit = issuedDate.split("T");

                arrayList.add(reportType + " (" + issuedDateSplit[0] + ")");
                DiagnosticReport temp = new DiagnosticReport(reportType,encounterNumber,issuedDateSplit[0],htmlcontent,diagnosticPdfUrl);
                diagnosticReportList.add(temp);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return diagnosticReportList;

    }

    public List<Allergy> parseAllergyIntolerance(String responseBody){

        JSONObject jo;
        allergyList = new ArrayList<>();

        try {

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

            JSONObject test;
            String allergySubstance, status, recordedDate, htmlcontent;
            ArrayList<String> arrayList = new ArrayList<>();

            for(int i=0; i<encounters.length(); i++){

                test = (JSONObject) encounters.get(i);
                allergySubstance = ((JSONObject)((JSONObject) test.get("resource")).get("substance")).get("text").toString();
                status = (((JSONObject) test.get("resource")).get("status")).toString();
                recordedDate = (((JSONObject) test.get("resource")).get("recordedDate")).toString();
                htmlcontent = ((JSONObject)((JSONObject) test.get("resource")).get("text")).get("div").toString();

                String[] recordedDateSplit = recordedDate.split("T");

                arrayList.add(allergySubstance + "(" + recordedDateSplit[0] + ")");
                Allergy temp = new Allergy(allergySubstance,status,recordedDateSplit[0],htmlcontent);
                allergyList.add(temp);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return allergyList;
    }

    public List<Appointment> parseAppointment(String responseBody){

        JSONObject jo;
        appointmentList = new ArrayList<>();

        try {

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

            JSONObject testappointment;
            String appointmentReason, appointmentStatus, appointmentDate, appointmentHtmlContent;
            ArrayList<String> arrayList = new ArrayList<>();

            for(int i=0; i<encounters.length(); i++){

                testappointment = (JSONObject) encounters.get(i);
                appointmentReason = ((JSONObject)((JSONObject) testappointment.get("resource"))).get("status").toString();
                appointmentStatus = ((JSONObject)((JSONObject) testappointment.get("resource"))).get("status").toString();
                appointmentDate = (((JSONObject) testappointment.get("resource")).get("start")).toString();
                appointmentHtmlContent = ((JSONObject)((JSONObject) testappointment.get("resource")).get("text")).get("div").toString();

                String[] recordedDateSplit = appointmentDate.split("T");

                arrayList.add(appointmentReason + "(" + recordedDateSplit[0] + ")");
                Appointment temp = new Appointment(appointmentReason,appointmentStatus,recordedDateSplit[0],appointmentHtmlContent);
                appointmentList.add(temp);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return appointmentList;
    }

    public List<Medications> parseMedications(String responseBody){

        JSONObject jo;
        medicationsList = new ArrayList<>();

        try {

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

            JSONObject test;
            String vaccineName, status, recordedDate, htmlcontent, encounterNumber;
            ArrayList<String> arrayList = new ArrayList<>();

            for(int i=0; i<encounters.length(); i++){

                test = (JSONObject) encounters.get(i);
                vaccineName = ((JSONObject)((JSONObject) test.get("resource")).get("vaccineCode")).get("text").toString();
                status = (((JSONObject) test.get("resource")).get("status")).toString();
                recordedDate = (((JSONObject) test.get("resource")).get("date")).toString();
                htmlcontent = ((JSONObject)((JSONObject) test.get("resource")).get("text")).get("div").toString();
                try{
                    encounterNumber = ((JSONObject)((JSONObject) test.get("resource")).get("encounter")).get("reference").toString();
                }catch (JSONException e){
                    encounterNumber = "";
                }
                String[] encounterNumberSplit = encounterNumber.split("/");

                String[] recordedDateSplit = recordedDate.split("T");

                arrayList.add(vaccineName + "(" + recordedDateSplit[0] + ")");
                if(encounterNumber.equals("")){
                    Medications temp = new Medications(vaccineName,recordedDateSplit[0],status,htmlcontent,"");
                    medicationsList.add(temp);
                }else{
                    Medications temp = new Medications(vaccineName,recordedDateSplit[0],status,htmlcontent,encounterNumberSplit[1]);
                    medicationsList.add(temp);
                }

                //Log.e("ViewAllActivity",encounterNumberSplit[1]);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return medicationsList;
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickMenu(View v){
        openDrawer(drawerLayout);
    }

    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public void clickProfile(View v){
        closeDrawer(drawerLayout);
    }

    public void clickLogout(View view){
        logout(this);
    }

    public void logout(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ViewAllActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void onClickEncounter(View view) {
        Intent intent = new Intent(ViewAllActivity.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataE);
        intent.putExtra("class", "Encounter");
        startActivity(intent);
    }

    public void onClickDiagnosticReport(View view) {
        Intent intent = new Intent(ViewAllActivity.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataDR);
        intent.putExtra("class", "DiagnosticReport");
        startActivity(intent);
    }

    public void onClickAllergies(View view) {
        Intent intent = new Intent(ViewAllActivity.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataA);
        intent.putExtra("class", "Allergies");
        startActivity(intent);
    }

    public void onClickMedications(View view) {
        Intent intent = new Intent(ViewAllActivity.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataM);
        intent.putExtra("class", "Medications");
        startActivity(intent);
    }

    public void onClickUploadDocuments(View view){
        Intent intent = new Intent(ViewAllActivity.this, Documents.class);
        startActivity(intent);
    }

    public void clickSetEmergencyMessage(View view){
        Intent intent = new Intent(ViewAllActivity.this, EmergencyMessage.class);
        startActivity(intent);
    }

}