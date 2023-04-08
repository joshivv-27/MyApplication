package com.example.abcd.myapplication.SyncDemo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.widget.Toast;

import com.example.abcd.myapplication.R;
import com.example.abcd.myapplication.SyncDemo.SyncMainActivity;

/**
 * Created by abcd on 1/27/17.
 */

public class MyService extends Service {

    int numMessages = 0;

    public MyService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        //Here I am creating a intent wich call sync main where the new data wolud be loaded when anyone touch notification/Automatically.

        Intent resultIntent = new Intent(this, SyncMainActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Sets an ID for the notification, so it can be updated
        int notifyID = 9001;

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Alert")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.mipmap.ic_launcher);

        // Set pending intent
        mNotifyBuilder.setContentIntent(resultPendingIntent);

        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        mNotifyBuilder.setDefaults(defaults);

        // Set the content for Notification
        mNotifyBuilder.setContentText(intent.getStringExtra("intntdata"));

        // Set autocancel
        mNotifyBuilder.setAutoCancel(true);

        // Post a notification
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());

    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }*/

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
