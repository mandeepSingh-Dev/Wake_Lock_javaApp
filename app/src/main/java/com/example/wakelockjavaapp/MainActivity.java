package com.example.wakelockjavaapp;

import static com.example.wakelockjavaapp.LocationService.LOCATION_MODE_KEY;
import static com.example.wakelockjavaapp.LocationService.wakeLock;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.Serializable;
import java.net.URLEncoder;

import kotlinx.coroutines.Delay;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
     /*   getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);*/

    }

    @SuppressLint("QueryPermissionsNeeded")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  turnScreenOn();
        setContentView(R.layout.activity_main);



        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new BlankFragment()).commit();


      /*  PowerManager powerManager1 = (PowerManager) getSystemService(POWER_SERVICE);
      @SuppressLint("InvalidWakeLockTag")
      PowerManager.WakeLock wakeLock =  powerManager1.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");
        wakeLock.acquire( *//*10 minutes*//*);*/



       // checkBatteryOptimization();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {

            setShowWhenLocked(true);
            setTurnScreenOn(true);


           /* KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);


            keyguardManager.requestDismissKeyguard(this, new KeyguardManager.KeyguardDismissCallback() {
                @Override
                public void onDismissSucceeded() {
                    super.onDismissSucceeded();
                    Toast.makeText(getApplicationContext(),"Succedd", Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        else {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


        AlarmManager alarmManager = (AlarmManager ) getSystemService(ALARM_SERVICE);
        /*Intent i = new Intent(this, MyReceiver2.class);
        i.setAction("ALARM_MY");

       PendingIntent pendingIntent = PendingIntent.getBroadcast(this,100,i,PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(5*1000L) ,pendingIntent);
       // alarmManager.setInexactRepeating();
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        }*/


        Intent serviceintent = new Intent(this,AlarmService.class);

        Intent broadcastIntent = new Intent(this, MyReceiver2.class);
        //broadcastIntent.putExtra("activity",(Serializable) this);
     //   broadcastIntent.putExtra("alarm", (Parcelable) this);

      //  PendingIntent pendingIntent = PendingIntent.getForegroundService(this,79,serviceintent,PendingIntent.FLAG_UPDATE_CURRENT);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,broadcastIntent,0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);


            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 1000L, pendingIntent);

            // alarmManager.setInexactRepeating();
        } else {
          //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        }

    }

    private void checkBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

            if (powerManager != null) {
                if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
                    @SuppressLint("BatteryLife")
                    Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 100);

                } else {
                  //  startReadingLocation(this.locationMode);
                    PowerManager powerManager1 = (PowerManager) getSystemService(POWER_SERVICE);
                 // PowerManager.WakeLock wakelock = powerManager1.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,MainActivity.class.getName());
                 // wakelock.acquire(10*60*1000L /*10 minutes*/);

                    Thread t1 = new Thread(new Runnable(){

                        @Override
                        public void run() {
                            for(int i=0;i<100;i++)
                            {
                                Log.d("difnvf", i+" f");
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if(i==99)
                                {
                                 //   wakelock.release();
                                }
                            }
                        }
                    });
                    t1.start();
                }
            }
        }
        else {
            Toast.makeText(this, "startReadingLocation  2ND ELSE", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("dfodd", resultCode+" "+requestCode);

        if(requestCode == 100)
        {

            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
           // PowerManager.WakeLock wakelock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,MainActivity.class.getName());
           // wakelock.acquire();

           Thread t1 = new Thread(new Runnable(){

               @Override
               public void run() {
                   for(int i=0;i<100;i++)
                   {
                       Log.d("difnvf", i+" ");
                       try {
                           Thread.sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

                       if(i==99)
                       {
                           //wakelock.release();
                       }
                   }
               }
           });
           t1.start();



        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
    private void  turnScreenOn() {
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        // Deprecated flags are required on some devices, even with API>=27
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

}




class LocationReceiver extends BroadcastReceiver {

    public static final String ACTION = "LOCATION_RECEIVER_ACTION";
    public static final int REQUEST_CODE = 10;

    @SuppressLint("WakelockTimeout")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && (ACTION.equals(intent.getAction()) || Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))) {
            PendingResult result = goAsync();

            if (LocationService.wakeLock == null) {
                PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                if (powerManager != null) {
                    LocationService.wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LocationService.class.getName());
                    LocationService.wakeLock.acquire();
                }
            }

            Intent locationIntent = new Intent(context, LocationService.class);
            locationIntent.putExtra(LOCATION_MODE_KEY, intent.getStringExtra(LOCATION_MODE_KEY));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                locationIntent.putExtra(LocationService.SHOW_NOTIFICATION, true);
                context.startForegroundService(locationIntent);
            } else {
                context.startService(locationIntent);
            }
            result.finish();
        }
    }
}