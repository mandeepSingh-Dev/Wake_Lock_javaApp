package com.example.wakelockjavaapp

import android.app.*
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AlarmService : Service() {

    private val notifID: Int = 101
    private val NOTIF_CHANNEL_ID_ALARM: Int = 10

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(notifID, buildRingNotification()!!, ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE)
        } else {
            startForeground(notifID, buildRingNotification())
        }
        return START_NOT_STICKY

    }


    //--------------------------------------------------------------------------------------------------
    private fun buildRingNotification(): Notification? {
        createNotificationChannel()

        val fullScreenIntent: Intent = Intent(this, AlertActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_UPDATE_CURRENT
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 3054, fullScreenIntent, flags)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, Integer.toString(NOTIF_CHANNEL_ID_ALARM))
            .setContentTitle(resources.getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setSmallIcon(R.drawable.ic_launcher_background)
         //   .setContentIntent(fullScreenPendingIntent)
            .setOnlyAlertOnce(true)
            .setFullScreenIntent(fullScreenPendingIntent, true)

        return builder.build()
    }

    //--------------------------------------------------------------------------------------------------
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(Integer.toString(NOTIF_CHANNEL_ID_ALARM), "CHANNEL", importance)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }
    }

/*
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show()

        val intent1 = Intent(this, AlertActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
       // startActivity(intent1)


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel("channel", "Alarm Manager", NotificationManager.IMPORTANCE_HIGH)

        notificationManager.createNotificationChannel(notificationChannel)



        val stopSerfice = Intent(this,AlarmService::class.java)
        val stopPndingSrfic = PendingIntent.getService(this,79,stopSerfice,PendingIntent.FLAG_CANCEL_CURRENT)




        val notificationBuilder = NotificationCompat.Builder(this, "channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm Title")
            .setContentText("Alarm Text")

         //   .setFullScreenIntent(pendingIntent, true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
           // .setContentIntent(stopPndingSrfic)

        val notification = notificationBuilder.build()

        val mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        mediaPlayer.start()

        notificationManager.notify(10,notification)

       // startForeground(100,notification)



        return START_STICKY
    }
*/


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}