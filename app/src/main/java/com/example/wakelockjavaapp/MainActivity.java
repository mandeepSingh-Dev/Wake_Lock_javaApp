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
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.text.TimeZoneFormat;
import android.icu.text.TimeZoneNames;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wakelockjavaapp.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.net.URLEncoder;

import kotlinx.coroutines.Delay;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;

    Context context = this;

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

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        else {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }



        AlarmManager alarmManager = (AlarmManager ) getSystemService(ALARM_SERVICE);

      //  Intent serviceintent = new Intent(this,AlarmService.class);

        Intent broadcastIntent = new Intent(this, MyReceiver2.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,broadcastIntent,0);


        binding.alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

              TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR,timePicker.getHour());
                        calendar.set(Calendar.MINUTE,timePicker.getMinute());
                        calendar.set(Calendar.SECOND,0);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(context));

              timePickerDialog.show();

              timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                  @Override
                  public void onDismiss(DialogInterface dialogInterface) {
                      Log.d("ikfndfd",String.valueOf(calendar.getTimeInMillis()));
                      Log.d("ikfndfd",String.valueOf(System.currentTimeMillis()));

                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                          alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                      } else {}
                  }
              });


            }
        });



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