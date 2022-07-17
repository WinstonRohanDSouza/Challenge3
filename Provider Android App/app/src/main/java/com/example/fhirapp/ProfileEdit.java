package com.example.fhirapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileEdit extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView changeProfilePhoto;
    EditText changeName;

    private TextView patientNameNavDrawer;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        initVar();

    }

    private void initVar() {

        drawerLayout = findViewById(R.id.drawer_layout_pe);
        changeProfilePhoto = findViewById(R.id.change_profile_photo);
        changeName = findViewById(R.id.change_name);
        patientNameNavDrawer = findViewById(R.id.patient_name);

        sharedPreferences = sharedPreferences = this.getSharedPreferences("PRAN", MODE_PRIVATE);

        setNameNavBar();

    }

    private void setNameNavBar() {

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
        Intent intent = new Intent(ProfileEdit.this, ProfileEdit.class);
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
                Intent intent = new Intent(ProfileEdit.this,
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
        Intent intent = new Intent(ProfileEdit.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataE);
        intent.putExtra("class", "Encounter");
        startActivity(intent);
    }

    public void onClickDiagnosticReport(View view) {
        Intent intent = new Intent(ProfileEdit.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataDR);
        intent.putExtra("class", "DiagnosticReport");
        startActivity(intent);
    }

    public void onClickAllergies(View view) {
        Intent intent = new Intent(ProfileEdit.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataA);
        intent.putExtra("class", "Allergies");
        startActivity(intent);
    }

    public void onClickMedications(View view) {
        Intent intent = new Intent(ProfileEdit.this, ViewAllActivity.class);
        //intent.putExtra("divTextEncounter", jsonDataM);
        intent.putExtra("class", "Medications");
        startActivity(intent);
    }

    public void onClickUploadDocuments(View v){
        Intent intent = new Intent(ProfileEdit.this, Documents.class);
        startActivity(intent);
        /*Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("/**");
        startActivityForResult(fileIntent,10);*/
    }

    public void clickSetEmergencyMessage(View view){
        Intent intent = new Intent(ProfileEdit.this, EmergencyMessage.class);
        startActivity(intent);
    }
}