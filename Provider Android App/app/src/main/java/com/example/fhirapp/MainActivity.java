package com.example.fhirapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);


        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname,pwd;
                uname = username.getText().toString();
                pwd = password.getText().toString();

                /*if(uname.equals("admin") && pwd.equals("Cerner123")){
                    Intent intent = new Intent(MainActivity.this, PatientDetails.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Incorrect password",Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(MainActivity.this, PatientDetails.class);
                startActivity(intent);

            }
        });

    }

}
//This is a test comment