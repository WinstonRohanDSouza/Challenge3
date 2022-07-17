package com.example.fhirapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import java.io.File;

public class ViewFile extends AppCompatActivity {

    private String fileLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent dataIntent = getIntent();
        fileLocation = dataIntent.getStringExtra("fileLocation");

        //open the file location
        File file = new File(  "/raw/storage/emulated/0/DCIM/Screenshots/IMG_20210601_091310.jpg");
        Uri fileURI = Uri.fromFile(file);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            fileURI = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".provider", file);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileURI, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
// /raw/storage/emulated/0/DCIM/Screenshots/IMG_20210601_091310.jpg