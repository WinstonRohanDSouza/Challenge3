package com.example.fhirapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmergencyMessage extends AppCompatActivity {

    private EditText emergencyMessage;
    private Button setEmergencyMessage;

    private static final String emergencyMessageKey = "EmergencyMessage";
    private static final String CHANNEL_ID = "PRANNotificationChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_message);

        emergencyMessage = findViewById(R.id.EmergencyMessageEditText);
        setEmergencyMessage = findViewById(R.id.SetEmergencyMessageButton);

        createNotificationChannel();

        setEmergencyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("PRAN", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(emergencyMessageKey, emergencyMessage.getText().toString());
                editor.commit();
                setNotification();
            }
        });
    }

    private void setNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences("PRAN", MODE_PRIVATE);
        String text = sharedPreferences.getString(emergencyMessageKey, "");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_pran)
                .setContentTitle("Emergency Message")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1,builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PRANNotificationChannel";
            String description = "PRANNotificationChannel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}