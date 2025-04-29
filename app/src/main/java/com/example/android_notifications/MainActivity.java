package com.example.android_notifications;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    //notification channel
    public static final String CHANNEL_ID = "Notification";
    public static final String CHANNEL_NAME = "Humna's Notification";
    public static final String CHANNEL_DESC = "New Channel";

    private TextInputEditText username;
    private TextInputEditText password;
    private FirebaseAuth mAuth;



//notification channel


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mychannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            mychannel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(mychannel);
        }

        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

            }
        });




    }

    private void createUser(){
        String email = username.getText().toString().trim();
        String mypassword = password.getText().toString().trim();
        if(email.isEmpty()){
            username.setError("email required");
        }
        if(mypassword.isEmpty()){
            username.setError("password required");

        }
        mAuth.createUserWithEmailAndPassword(email,mypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: User is registered ");
                    startProfileActivity();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                         userLogin(email,mypassword);

                    }else{
                        Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
            }
        });


    }

    private void userLogin(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startProfileActivity();
                }
                else{
                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if(mAuth!=null){
//            startProfileActivity();
//        }
    }

    private void startProfileActivity(){
        Intent intent  = new Intent(this,Profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}