package com.example.fhirapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class showSectionDetails extends AppCompatActivity {

    TextView displaySectionDetails;
    PDFView diagnostic_report_pdf;
    byte[] pdfbyteData;
    private RequestQueue mQueue;
    Button view_Pdf;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_section_details);

//        //RealM Migration
//        Realm.init(getApplicationContext());
//
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        int versionCode = sharedPreferences.getInt("VERSION_CODE", 0);
//        String versionName = sharedPreferences.getString("VERSION_NAME", "0");
//        int realmVersion = 0;
//        if(versionCode != BuildConfig.VERSION_CODE || versionName.compareToIgnoreCase(BuildConfig.VERSION_NAME) != 0) {
//            realmVersion++;
//        } else if (realmVersion < 0) {
//            realmVersion = 0;
//        }
//        sharedPreferences.edit()
//                .putInt("VERSION_CODE", BuildConfig.VERSION_CODE)
//                .putString("VERSION_NAME", BuildConfig.VERSION_NAME)
//                .apply();
//        RealmConfiguration config = new RealmConfiguration.Builder().name("FileNotes.realm")
//                .migration(new RealmMigrations())
//                .schemaVersion(realmVersion)
//                .build();
//
//        Realm.setDefaultConfiguration(config);
//
//        Realm realm = Realm.getInstance(config);
//
//
//
//
//        //New code

        //Old Code
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        //Old Code

        displaySectionDetails = findViewById(R.id.displaySectionText);
        drawerLayout = findViewById(R.id.drawer_layout_ssd);

        diagnostic_report_pdf = findViewById(R.id.diagnostic_report_pdf);
        view_Pdf = findViewById(R.id.view_Pdf);

        Intent intent = getIntent();
        String text = intent.getStringExtra("divTextEncounter");
        String diagnosticPdfUrl = intent.getStringExtra("diagnosticPdfUrl");
        String diagnosticPdfTitle = intent.getStringExtra("diagnosticPdfTitle");
        Log.d("diagnosticPdfUrl","diagnosticPdfUrl");

        Spanned spannedText = Html.fromHtml(text);
        displaySectionDetails.setText(spannedText);

        if (diagnosticPdfUrl != null)
        {
            //getPdf(diagnosticPdfUrl);
            view_Pdf.setVisibility(View.VISIBLE);

            view_Pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    diagnostic_report_pdf.setVisibility(View.VISIBLE);
                    displaySectionDetails.setVisibility(View.GONE);
                    view_Pdf.setVisibility(View.GONE);

                    getPdf(diagnosticPdfUrl, realm,diagnosticPdfTitle);




                }
            });

        }



    }

    public void getPdf(String diagnosticPdfUrl, Realm realm, String diagnosticPdfTitle) {

        InputStreamVolleyRequest request = new     InputStreamVolleyRequest(Request.Method.GET, diagnosticPdfUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response!=null) {

                                diagnostic_report_pdf.fromBytes(response).load();
                                Log.d("ResponsePDF",response.toString());

                                //New code
                                realm.beginTransaction();

                                FileNotes fileNotes =realm.createObject(FileNotes.class);

                                fileNotes.setDiagnosticPdfReport(response);

                                fileNotes.setDocType("application/pdf");
                                fileNotes.setTitle(diagnosticPdfTitle);
                                long createdTime = System.currentTimeMillis();
                                fileNotes.setCreatedtime(createdTime);

                                realm.commitTransaction();




                                //New code

                                FileOutputStream outputStream;
                                String name= diagnosticPdfTitle +String.valueOf(createdTime)+".pdf";
                                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                                outputStream.write(response);
                                outputStream.close();
                                Toast.makeText(showSectionDetails.this, "PDF File is loaded", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            Toast.makeText(showSectionDetails.this, "UNABLE TO DOWNLOAD FILE.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
// TODO handle the error
                Toast.makeText(showSectionDetails.this, "UNABLE TO LOAD FILE", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }, null)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/pdf");;

                return params;
            }
        };

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);



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
                Intent intent = new Intent(showSectionDetails.this,
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
        Intent intent = new Intent(showSectionDetails.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataE);
        intent.putExtra("class", "Encounter");
        startActivity(intent);
    }

    public void onClickDiagnosticReport(View view) {
        Intent intent = new Intent(showSectionDetails.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataDR);
        intent.putExtra("class", "DiagnosticReport");
        startActivity(intent);
    }

    public void onClickAllergies(View view) {
        Intent intent = new Intent(showSectionDetails.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataA);
        intent.putExtra("class", "Allergies");
        startActivity(intent);
    }

    public void onClickMedications(View view) {
        Intent intent = new Intent(showSectionDetails.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataM);
        intent.putExtra("class", "Medications");
        startActivity(intent);
    }

    public void onClickUploadDocuments(View view){

        Intent intent = new Intent(showSectionDetails.this, Documents.class);
        startActivity(intent);
    }

    public void clickSetEmergencyMessage(View view){
        Intent intent = new Intent(showSectionDetails.this, EmergencyMessage.class);
        startActivity(intent);
    }
}