package com.example.myapplication.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;



public class AppService extends Service {
    public static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";
    public static final String STOP_FOREGROUND_SERVICE = "STOP_FOREGROUND_SERVICE";

    public static String CHANNEL_ID = "com.example.myapplication.example.myapplication.myapplication.CHANNEL_ID_FOREGROUND";
    public static String MAIN_ACTION = "com.example.myapplication.example.myapplication.myapplication.appService.action.main";

    public static final String BROADCAST_NEW_MASSAGE_DETECTED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String BROADCAST_NEW_CALL_DETECTED = "android.intent.action.PHONE_STATE";

    public static int NOTIFICATION_ID = 153;
    private int lastShownNotificationId = -1;

    private NotificationCompat.Builder notificationBuilder;
    private ReceiveSmsAndCall receiveSmsAndCall;

    private boolean isServiceRunningRightNow = false;

    public AppService() {
    }

    @Override
    public void onCreate() {
        receiveSmsAndCall = new ReceiveSmsAndCall();
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BROADCAST_NEW_MASSAGE_DETECTED);
        intentFilter.addAction(BROADCAST_NEW_CALL_DETECTED);

        registerReceiver(receiveSmsAndCall, intentFilter);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(START_FOREGROUND_SERVICE)) {
            if (isServiceRunningRightNow) {
                return START_STICKY;
            }

            isServiceRunningRightNow = true;
            notifyToUserForForegroundService();

            return START_STICKY;
        } else if (intent.getAction().equals(STOP_FOREGROUND_SERVICE)) {
            stopForeground(true);
            stopSelf();

            isServiceRunningRightNow = false;

            return START_NOT_STICKY;
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiveSmsAndCall);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void notifyToUserForForegroundService() {
        // On notification click
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder = getNotificationBuilder(this,
                CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top

//        notificationBuilder.setContentIntent(pendingIntent) // Open activity
//                .setOngoing(false)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
//                .setContentTitle(" ");
//

        notificationBuilder.setContentIntent(pendingIntent) // Open activity
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_background);


        Notification notification = notificationBuilder.build();

        startForeground(NOTIFICATION_ID, notification);

        if (NOTIFICATION_ID != lastShownNotificationId) {
            // Cancel previous notification
            final NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
            notificationManager.cancel(lastShownNotificationId);
        }
        lastShownNotificationId = NOTIFICATION_ID;
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        return builder;
    }

    @TargetApi(26)
    private static void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        final NotificationManager nm = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        String notifications_channel_description = "Cycling map channel";
        String description = notifications_channel_description;


        if (nm != null) {
            NotificationChannel nChannel = nm.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(description);

                // from another answer
                nChannel.enableLights(true);
                nChannel.setLightColor(Color.BLUE);

                nm.createNotificationChannel(nChannel);
            }
        }
    }
}