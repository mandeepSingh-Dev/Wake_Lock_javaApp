package com.example.wakelockjavaapp;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.POWER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import java.nio.file.WatchKey;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyReceiver2 extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "com.example.wakelockjavaapp::AlarmSericeTag");
        wakeLock.acquire(60000);

        Intent intent1 = new Intent(context, AlarmService.class);
        ContextCompat.startForegroundService(context, intent1);

    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ifndfv", "dfin");
        Toast.makeText(context, "Alarmed", Toast.LENGTH_SHORT).show();

*//*
        Intent intenttt = new Intent(context,AlarmService.class);
        context.startService(intenttt);
*//*


        Intent mathAlarmServiceIntent = new Intent(context, AlarmServiceBroadcastReciever.class);
       // context.sendBroadcast(mathAlarmServiceIntent,null);


        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myalarmapp:alarm.");
        wl.acquire(60000);

        Intent mathAlarmAlertActivityIntent;
        mathAlarmAlertActivityIntent = new Intent(context, AlertActivity.class);
       // mathAlarmAlertActivityIntent.putExtra("alarm", alarm);
        mathAlarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


       // context.startActivity(mathAlarmAlertActivityIntent);
          wl.release();


        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,mathAlarmAlertActivityIntent,0);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = new NotificationChannel("channel", "Alarm Manager", NotificationManager.IMPORTANCE_HIGH);

        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,"channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Alarm Title")
                .setContentText("Alarm Text")
                .setFullScreenIntent(pendingIntent, true)
                .setCategory(NotificationCompat.CATEGORY_ALARM);

        Notification notification = notificationBuilder.build();

        notificationManager.notify(100,notification);

       *//* PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myalarmapp:alarm.");
        wl.acquire(600000);

        Intent intent1 = new Intent(context,AlertActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent1);




        MediaPlayer mediaPlayer = MediaPlayer.create(context,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        mediaPlayer.start();
*//*
      //  wl.release();

    }*/
}