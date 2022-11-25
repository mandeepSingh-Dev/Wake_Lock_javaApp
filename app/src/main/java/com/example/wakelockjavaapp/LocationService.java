package com.example.wakelockjavaapp;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.Nullable;

public class LocationService extends Service {

    public static final String LOCATION_MODE_KEY = "LOCATION_MODE_KEY";
    public static final String SHOW_NOTIFICATION = "SHOW_NOTIFICATION";
    private static final long LOCATION_ALARM_PERIOD = 60 * 60 * 1000;
    public static PowerManager.WakeLock wakeLock;
    // private LocationHelperFactory.Mode mode = LocationHelperFactory.Mode.LEGACY;
    // private LocationHelperFactory locationHelperFactory;
    // private LocationProcessingUnit locationProcessingUnit;

    @Override
    public void onCreate() {
        super.onCreate();
        // this.locationHelperFactory = new LocationHelperFactory();
        // this.locationProcessingUnit = new LocationProcessingUnit(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.hasExtra(LOCATION_MODE_KEY) && intent.getStringExtra(LOCATION_MODE_KEY) != null) {
                //  this.mode = LocationHelperFactory.Mode.valueOf(intent.getStringExtra(LOCATION_MODE_KEY));
            }
            if (intent.getBooleanExtra(SHOW_NOTIFICATION, false)) {
         //       startForeground(4, NotificationManager.createNotification(this, getResources().getString(R.string.receiving_location_update)));
            }
        }
       // readLocation();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, LocationReceiver.class);
        intent.setAction(LocationReceiver.ACTION);
       // intent.putExtra(LOCATION_MODE_KEY, this.mode.toString());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, LocationReceiver.REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + LOCATION_ALARM_PERIOD, alarmIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + LOCATION_ALARM_PERIOD, alarmIntent);
            } else {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + LOCATION_ALARM_PERIOD, alarmIntent);
            }

        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
    }


    private void stopService() {
        stopForeground(true);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}