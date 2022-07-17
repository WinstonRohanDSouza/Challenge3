package com.example.fhirapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.DocumentHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PatientDetails extends AppCompatActivity {

    private HttpURLConnection connection;
    private TextView patientNameNavDrawer;
    private TextView patientDisplayName,patientDisplayDOB,patientDisplayGender,patientDisplayMaritalStatus,patientDisplayStatus;
    private URL dstu2_encounters, dstu2_allergy, dstu2_diagnostic,dstu_patientDetails,dstu_medications, dstu_appointment;

    private String jsonDataE, jsonDataDR,jsonDataA,jsonDataM,jsonDataApmnt;
    private List<Encounter> encountersList;
    private List<DiagnosticReport> diagnosticReportList;
    private List<Allergy> allergyList;
    private List<Medications> medicationsList;
    private List<Appointment> appointmentList;
    private Patient patient;

    private List<String> titleValuesE, titleValuesDR, titleValuesA, titleValuesM, titleValuesApmnt;
    private List<String> dateValuesE, dateValuesDR, dateValuesA, dateValuesM, dateValuesAppmnt;

    private PatientDocumentHandler documentHandler;
    SharedPreferences sharedPreferences;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        initVars();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                messageTransferHandler();

                fetchData();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //setTitleAndDate();
        //setFileRecyclerView();
    }

    public List<Encounter> getEncountersList() {
        return encountersList;
    }

    public List<DiagnosticReport> getDiagnosticReportList() {
        return diagnosticReportList;
    }

    public List<Allergy> getAllergyList() {
        return allergyList;
    }

    public List<Medications> getMedicationsList() {
        return medicationsList;
    }

//    private void setTitleAndDate() {
//        for(int i=0; i<encountersList.size();i++){
//
//        }
//        Log.e("PatientDetails", "EncounterList Size: setTitleAndDate:" + String.valueOf(encountersList.size()));
//    }

    private void fetchData() {
        try {
            startThread(dstu2_encounters, "encounter");
            startThread(dstu2_diagnostic, "diagnosticReport");
            startThread(dstu2_allergy, "allergyIntolerance");
            startThread(dstu_patientDetails,"patientDetails");
            startThread(dstu_medications,"medication");
            startThread(dstu_appointment,"appointment");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void messageTransferHandler() {
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("theMessage");
                String type = intent.getStringExtra("type");

                if(type.equals("encounter")){

                    jsonDataE = message;
                    parseEncounter(message);

                }else if(type.equals("diagnosticReport")){

                    jsonDataDR = message;
                    Log.d("Inside", "diagnosticReport");
                    parseDiagnosticReport(message);

                }else if(type.equals("allergyIntolerance")){


                    jsonDataA = message;
                    Log.d("Inside", "allergyIntolerance");
                    parseAllergyIntolerance(message);

                }else if(type.equals("medication")){

                    jsonDataM = message;
                    Log.d("Inside", "medication");
                    parseMedications(message);

                }else if(type.equals("patientDetails")){

                    parsePatientDetails(message);

                }else if (type.equals("appointment")){
                    jsonDataApmnt = message;
                    Log.d("Inside", "Appointment");
                    //parseAppointment(message);
                }

                else{

                }


            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("incomingMessage"));
    }

    private void initVars() {

        //Navigation Drawer
        patientNameNavDrawer = findViewById(R.id.patient_name);
        drawerLayout = findViewById(R.id.drawer_layout_pd);

        documentHandler = new PatientDocumentHandler(this);

        //Patient Data Display
        patientDisplayName = findViewById(R.id.patientDisplayName);
        patientDisplayDOB = findViewById(R.id.patientDisplayDOB);
        patientDisplayGender = findViewById(R.id.patientDisplayGender);
        patientDisplayMaritalStatus = findViewById(R.id.patientDisplayMaritalStatus);
        patientDisplayStatus = findViewById(R.id.patientDisplayStatus);

        //RecylerView data
        titleValuesE = new ArrayList<>();
        dateValuesE = new ArrayList<>();
        titleValuesDR = new ArrayList<>();
        dateValuesDR = new ArrayList<>();
        titleValuesA = new ArrayList<>();
        dateValuesA = new ArrayList<>();
        titleValuesM = new ArrayList<>();
        dateValuesM = new ArrayList<>();
        titleValuesApmnt = new ArrayList<>();
        dateValuesAppmnt = new ArrayList<>();

        //Lists
        encountersList = new ArrayList<>();

        //Dummy values
        /*for(int i=0; i<10; i++){
            titleValues.add("Title" + i);
            dateValues.add("Date" + i);
        }*/

        //API URL addresses
        try {
            dstu2_diagnostic = new URL("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/DiagnosticReport?patient=12724066&_count=10");
            dstu2_encounters = new URL("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/Encounter?patient=12724066");
            dstu2_allergy = new URL("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/AllergyIntolerance?patient=12724066");
            dstu_patientDetails = new URL("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/Patient?_id=12724066");
            dstu_medications = new URL("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/Immunization?patient=12724066");
            dstu_appointment = new URL("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/Appointment?date=2021-07&patient=12724066");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void startThread(URL url, String type) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    try {

                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setReadTimeout(10000);
                        connection.setConnectTimeout(15000);
                        connection.connect();
                        Log.e("PatientDetails", "connection successful: " + type);

                        int connectionResponse = connection.getResponseCode();

                        BufferedReader reader;
                        String line;
                        StringBuffer responseContent = new StringBuffer();

                        if(connectionResponse > 299){

                            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            while((line = reader.readLine()) != null){
                                responseContent.append(line);
                            }
                            reader.close();

                        } else {

                            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            while((line = reader.readLine()) != null){
                                responseContent.append(line);
                            }
                            reader.close();

                        }

                        Intent incomingMessageIntent = new Intent("incomingMessage");
                        incomingMessageIntent.putExtra("theMessage", responseContent.toString());
                        incomingMessageIntent.putExtra("type", type);
                        LocalBroadcastManager.getInstance(PatientDetails.this).sendBroadcast(incomingMessageIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        connection.disconnect();
                        Log.e("PatientDetails", "connection closed" + type);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread.join();
    }

    public String parsePatientDetails(String responseBody) {

        JSONObject jo;

        try {

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

            JSONObject test;
            String htmlcontent, patientName,dob,gender,maritalStatus,status,id;

            for(int i=0; i<encounters.length(); i++){

                test = (JSONObject) encounters.get(i);

                htmlcontent = ((JSONObject)((JSONObject) test.get("resource")).get("text")).get("div").toString();
                patientName = ((JSONObject)((JSONArray)((JSONObject) test.get("resource")).get("name")).get(0)).get("text").toString();
                dob = ((JSONObject) test.get("resource")).get("birthDate").toString();
                gender = ((JSONObject) test.get("resource")).get("gender").toString();
                maritalStatus = ((JSONObject)((JSONObject) test.get("resource")).get("maritalStatus")).get("text").toString();
                String statusBinary =  ((JSONObject) test.get("resource")).get("active").toString();
                id = ((JSONObject) test.get("resource")).get("id").toString();

                if(statusBinary.equals("true")){
                    status = "active";
                }else{
                    status = "inactive";
                }

                String text = Html.fromHtml(htmlcontent).toString();
                patient = new Patient(patientName,dob,gender,maritalStatus,status,id,text);


                patientDisplayDOB.setText(dob);
                patientDisplayGender.setText(gender);
                patientDisplayMaritalStatus.setText(maritalStatus);
                patientDisplayStatus.setText(status);

                patientNameNavDrawer.setText(patientName);

                sharedPreferences = sharedPreferences = this.getSharedPreferences("PRAN", MODE_PRIVATE);
                if(!sharedPreferences.contains("PatientName") || sharedPreferences.getString("PatientName","").equals("")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PatientName", patientName);
                    patientDisplayName.setText(patientName);
                    editor.commit();
                }else{
                    String temp = sharedPreferences.getString("PatientName", "");
                    patientDisplayName.setText(temp);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return responseBody;
    }

    public List<Encounter> parseEncounter(String responseBody){

        JSONObject jo;


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
            Log.e("PatientDetails", "EncounterList size: parseEncounter: " + String.valueOf(encountersList.size()));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<encountersList.size(); i++){
            titleValuesE.add(encountersList.get(i).getCheifComplaint());
            dateValuesE.add(encountersList.get(i).getDate());
        }

        //RealmDB for Encounter



        //RealmDB for Encounter


        //Encounter
        LinearLayoutManager layoutManagerE = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewE = findViewById(R.id.recyclerViewEncounters);
        recyclerViewE.setLayoutManager(layoutManagerE);
        RecyclerViewAdapter adapterE = new RecyclerViewAdapter(titleValuesE,dateValuesE,this,encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList,"Encounter",6);
        recyclerViewE.setAdapter(adapterE);

        return encountersList;
    }

    public List<DiagnosticReport> parseDiagnosticReport(String responseBody){

        JSONObject jo;
        diagnosticReportList = new ArrayList<>();

        try {

            jo = new JSONObject(responseBody);

            JSONArray diagnostics = (JSONArray) jo.get("entry");

            Log.e("parseDiagnosticReportArray", String.valueOf(diagnostics));

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


            Log.e("PatientDetails", "TitleValues size: parseDiagnosticReport:" + String.valueOf(titleValuesDR.size()));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<diagnosticReportList.size(); i++){
            titleValuesDR.add(diagnosticReportList.get(i).getReportType());
            dateValuesDR.add(diagnosticReportList.get(i).getIssuedDate());
        }

        //Diagnostic Report
        LinearLayoutManager layoutManagerDR = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewDR = findViewById(R.id.recyclerViewDiagnostics);
        recyclerViewDR.setLayoutManager(layoutManagerDR);
        RecyclerViewAdapter adapterDR = new RecyclerViewAdapter(titleValuesDR,dateValuesDR,this,encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList,"DiagnosticReport",6);
        recyclerViewDR.setAdapter(adapterDR);

        return diagnosticReportList;
    }

    public List<Allergy> parseAllergyIntolerance(String responseBody){

        JSONObject jo;
        allergyList = new ArrayList<>();
        Log.d("StringJSONAllergy",responseBody);
        try {

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

            Log.d("JSONARRAYALLERGY",encounters.toString());

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

//                Log.d("Allappmsndetails5",allergySubstance);
//                Log.d("Allappmsndetails6",status);
//                Log.d("Allappmsndetails7",recordedDateSplit[0]);
//                Log.d("Allappmsndetails8",htmlcontent);

                arrayList.add(allergySubstance + "(" + recordedDateSplit[0] + ")");
                Allergy temp = new Allergy(allergySubstance,status,recordedDateSplit[0],htmlcontent);
                allergyList.add(temp);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<allergyList.size(); i++){
            titleValuesA.add(allergyList.get(i).getAllergySubstance());
            dateValuesA.add(allergyList.get(i).getRecordedDate());
        }

//        Log.d("SizeofAllergy", String.valueOf(allergyList.size()));



        //Allergies
        LinearLayoutManager layoutManagerA = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewA = findViewById(R.id.recyclerViewAllergies);
        recyclerViewA.setLayoutManager(layoutManagerA);
        RecyclerViewAdapter adapterA = new RecyclerViewAdapter(titleValuesA,dateValuesA,this,encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList,"Allergies",6);
        recyclerViewA.setAdapter(adapterA);

        return allergyList;
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
                Log.e("ViewAllActivity",encounterNumber);
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

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<medicationsList.size(); i++){
            titleValuesM.add(medicationsList.get(i).getVaccineName());
            dateValuesM.add(medicationsList.get(i).getDate());
        }

        //Medications
        LinearLayoutManager layoutManagerM = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewM = findViewById(R.id.recyclerViewMedications);
        recyclerViewM.setLayoutManager(layoutManagerM);
        RecyclerViewAdapter adapterM = new RecyclerViewAdapter(titleValuesM,dateValuesM,this,encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList,"Medications",6);
        recyclerViewM.setAdapter(adapterM);

        Log.d("PatientDetails","RecyclerView setup complete");

        return medicationsList;
    }


    public List<Appointment> parseAppointment(String responseBody){

//        Log.d("Inside","parseAppointment method");

        JSONObject jo;
        appointmentList = new ArrayList<>();
//        Log.d("StringJSONAppointment",responseBody);

        try {

//            Log.d("Inside","parseAppointment method try");

            jo = new JSONObject(responseBody);

            JSONArray encounters = (JSONArray) jo.get("entry");

//          Log.d("JSONARRAYAPPMNT",encounters.toString());

            JSONObject testappointment;
            String appointmentReason, appointmentStatus, appointmentDate, appointmentHtmlContent;
            ArrayList<String> arrayList = new ArrayList<>();

            for(int i=0; i<encounters.length(); i++){

//                Log.d("Inside","parseAppointment method for loop");

                testappointment = (JSONObject) encounters.get(i);
                appointmentReason = ((JSONObject)((JSONObject) testappointment.get("resource"))).get("status").toString();
                appointmentStatus = ((JSONObject)((JSONObject) testappointment.get("resource"))).get("status").toString();
                appointmentDate = (((JSONObject) testappointment.get("resource")).get("start")).toString();
                appointmentHtmlContent = ((JSONObject)((JSONObject) testappointment.get("resource")).get("text")).get("div").toString();

                String[] recordedDateSplit = appointmentDate.split("T");

//                Log.d("Allappmsndetails1",appointmentReason);
//                Log.d("Allappmsndetails2",appointmentStatus);
//                Log.d("Allappmsndetails3",recordedDateSplit[0]);
//                Log.d("Allappmsndetails4",appointmentHtmlContent);


                arrayList.add(appointmentReason + "(" + recordedDateSplit[0] + ")");
                Appointment temp = new Appointment(appointmentReason,appointmentStatus,recordedDateSplit[0],appointmentHtmlContent);
                appointmentList.add(temp);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<appointmentList.size(); i++){
            titleValuesApmnt.add(appointmentList.get(i).getAppointmentReason());
            dateValuesAppmnt.add(appointmentList.get(i).getAppointmentDate());
        }

//        Log.d("Size", String.valueOf(appointmentList.size()));

        //Appointment
        /*LinearLayoutManager layoutManagerA = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL,false);
        //RecyclerView recyclerViewA = findViewById(R.id.recyclerViewAppointments);
        recyclerViewA.setLayoutManager(layoutManagerA);
        RecyclerViewAdapter adapterA = new RecyclerViewAdapter(titleValuesApmnt,dateValuesAppmnt,this,encountersList,diagnosticReportList,allergyList,medicationsList,appointmentList,"Appointment",6);
        recyclerViewA.setAdapter(adapterA);*/

        return appointmentList;
    }

    public void clickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickProfile(View v){
        Intent intent = new Intent(PatientDetails.this, ProfileEdit.class);
        startActivity(intent);
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
                Intent intent = new Intent(PatientDetails.this,
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

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void onClickEncounter(View view) {
        Intent intent = new Intent(PatientDetails.this, ViewAllActivity.class);
        intent.putExtra("divTextEncounter", jsonDataE);
        intent.putExtra("class", "Encounter");
        startActivity(intent);
    }

    public void onClickDiagnosticReport(View view) {
        Intent intent = new Intent(PatientDetails.this, ViewAllActivity.class);
        intent.putExtra("divTextEncounter", jsonDataDR);
        intent.putExtra("class", "DiagnosticReport");
        startActivity(intent);
    }

    public void onClickAllergies(View view) {
        Intent intent = new Intent(PatientDetails.this, ViewAllActivity.class);
        intent.putExtra("divTextEncounter", jsonDataA);
        intent.putExtra("class", "Allergies");
        startActivity(intent);
    }

    public void onClickMedications(View view) {
        Intent intent = new Intent(PatientDetails.this, ViewAllActivity.class);
        intent.putExtra("divTextEncounter", jsonDataM);
        intent.putExtra("class", "Medications");
        startActivity(intent);
    }

    public void onClickAppointmentDetails(View view) {
        Intent intent = new Intent(PatientDetails.this, ViewAllActivity.class);
        intent.putExtra("divTextEncounter", jsonDataApmnt);
        intent.putExtra("class", "Appointment");
        startActivity(intent);


    }

    public void onClickSlotDetails(View view) {
        Intent intent = new Intent(PatientDetails.this, SlotActivity.class);
//        intent.putExtra("divTextEncounter", jsonDataApmnt);
//        intent.putExtra("class", "Appointment");
        startActivity(intent);


    }

    public void onClickUploadDocuments(View v){
        Intent intent = new Intent(PatientDetails.this, Documents.class);
        startActivity(intent);
        /*Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("/**");
        startActivityForResult(fileIntent,10);*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK){
                    String location = data.getData().getPath();
                    Path path = Paths.get(location);
                    String fileName = path.getFileName().toString();
                    String date = "";
                    try {
                        FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
                        date = creationTime.toString();
                    } catch (IOException ex) {
                        // handle exception
                    }
                    documentHandler.addFile(location,fileName,date);
                }
                setFileRecyclerView();
                break;
        }

    }*/

    /*private void setFileRecyclerView(){
        List<String> fileName = documentHandler.getDocumentName();
        List<String> fileLocation = documentHandler.getDocumentDir();
        List<String> fileDate = documentHandler.getDocumentDate();

        for(int i=0; i<fileName.size(); i++){
            Log.e("PatientDetails", "filename: " + fileName.get(i));
            Log.e("PatientDetails", "filelocation: " + fileLocation.get(i));
            Log.e("PatientDetails", "filedate: " + fileDate.get(i));
        }

        LinearLayoutManager layoutManagerM = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewM = findViewById(R.id.recyclerViewDocuments);
        recyclerViewM.setLayoutManager(layoutManagerM);
        DocumentRecyclerViewAdapter adapterM = new DocumentRecyclerViewAdapter(fileName,fileDate,this,fileLocation);
        recyclerViewM.setAdapter(adapterM);

        Log.d("PatientDetails","RecyclerView setup complete");
    }*/

    public void clickSetEmergencyMessage(View view){
        Intent intent = new Intent(PatientDetails.this, EmergencyMessage.class);
        startActivity(intent);
    }


}