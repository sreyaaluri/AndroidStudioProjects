package com.example.habitbuilder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class sendNotification extends BroadcastReceiver { //makes it a broadcast receiver

    // The below parameters are some user-defined constants for creating notifications.
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    public static final String CHANNEL_ID = "Channel1";
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;

    public void onReceive (Context context , Intent intent) { //called when the BroadcastReceiver object receives an Intent broadcast .

        //create an instance of NotificationManager, call getSystemService(), passing in the NOTIFICATION_SERVICE constant.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;

        String identity = intent.getStringExtra("IDENTITY");
        Notification notification = getNotification(context,"You are a "+identity+"! ^_^") ;

        //we add a constraint - the device running the app has Android SDK 26 or up
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , NotificationManager.IMPORTANCE_HIGH) ;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        notificationManager.notify(0 , notification) ; // Here, notification id is user-defined. deliver the notification
    }

    /**
     * method to create notification and its content
     * @param context
     * @param content
     * @return customized notification
     */
    private Notification getNotification (Context context, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id ) ;
        builder.setContentTitle( "Habit Reminder" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ; //the notification cancels itself automatically
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;  //return notification object
    }
}