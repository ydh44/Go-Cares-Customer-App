package com.caregiver.gocares;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String dataPayload = data.get("data");
        /*
         * Cek jika notif berisi data payload
         * pengiriman data payload dapat dieksekusi secara background atau foreground
         */
        if (remoteMessage.getData().size() > 0) {
            Log.e("TAG", "Message data payload: " + remoteMessage.getData());

            try {
                JSONObject jsonParse = new JSONObject(dataPayload);
                showNotif(jsonParse.getString("title"), jsonParse.getString("message"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
         * Cek jika notif berisi data notification payload
         * hanya dieksekusi ketika aplikasi bejalan secara foreground
         * dan dapat push notif melalui UI Firebase console
         */
        if (remoteMessage.getNotification() != null) {
            Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotif(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

    }

    public void showNotif(String title, String message){
        String NOTIFICATION_CHANNEL_ID = "channel_me";
        Context context = this.getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Android Notif Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent mIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo2)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setTicker("notif starting")
                .setAutoCancel(true)
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        SessionLog.SaveFcm(this, token);
    }
}
