package com.example.android_notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {
    private ImageView my_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        my_profile_image = findViewById(R.id.profile_image);

        my_profile_image.setImageResource(R.drawable.ic_launcher_foreground);


    }
}