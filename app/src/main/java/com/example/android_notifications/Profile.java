package com.example.android_notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Profile extends AppCompatActivity {
    private ImageView my_profile_image;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        my_profile_image = findViewById(R.id.profile_image);

        my_profile_image.setImageResource(R.drawable.ic_launcher_foreground);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    saveToken(token);
                    Log.d("FCM", "Token: " + token);
                });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
        Intent intent  = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);}
    }

    private void saveToken(String token){
        String email = mAuth.getCurrentUser().getEmail();

        Users user = new Users(email,token);
        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
        dbUsers.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Profile.this, "user added",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}