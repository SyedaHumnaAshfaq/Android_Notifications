package com.example.android_notifications;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.NoRouteToHostException;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "";

//    public void onNewToken(@NonNull String token) {
//        super.onNewToken(token);
//
//        // Log the token to check it
//        Log.d("FCM_TOKEN", "New token: " + token);
//
//        // Optionally, save this token to your server if needed
//        // You can send this token to your server for later use
//        // e.g., sendTokenToServer(token);
//    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

//        Toast.makeText(getApplicationContext(),"Message recieved",Toast.LENGTH_LONG).show();

        Log.d(TAG, "onMessageReceived: ");
        if(message.getNotification()!=null){
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();
            NotificationHelper.display_notification(getApplicationContext(),title,body);
        }
    }


}
