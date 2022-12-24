package com.example.wakelockjavaapp

import android.app.KeyguardManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wakelockjavaapp.databinding.ActivityAlertBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

class AlertActivity : AppCompatActivity() {

    val mainDispather = Dispatchers.Main
   // lateinit var binding:ActivityAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this,"  ALERT  ",Toast.LENGTH_SHORT).show()
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setTurnScreenOn(true)
            setShowWhenLocked(true)
            
        }
        /** This 'super.onCreate' , 'Binding Inflate' & 'setContetnView()' should be below setTurnedScrfeenOn and setShowWhenLocked */
        super.onCreate(savedInstanceState)
      //  binding = ActivityAlertBinding.inflate(LayoutInflater.from(this))
        setContentView(R.layout.activity_alert)
/*

        binding.textVirew.setOnClickListener {
            SingletonClass._trigger.value = true
            finish()
        }

        binding.sendButton.setOnClickListener {

            val packageManager = packageManager
            val i = Intent(Intent.ACTION_VIEW)

            try {
                val url = "https://api.whatsapp.com/send?phone=" + "919654980621" + "&text=" + URLEncoder.encode("Tiffin", "UTF-8")
                i.setPackage("com.whatsapp")
                i.data = Uri.parse(url)
                //    if (i.resolveActivity(packageManager) != null) {
                startActivity(i)

                //  }
            } catch (e: Exception) {
                Log.d("dofndd", e.message.toString())
                e.printStackTrace()
            }
            SingletonClass._trigger.value  = true
            finish()
        }
        binding.cancelButton.setOnClickListener {
            SingletonClass._trigger.value = true
            finish()
        }



*/

    }

    private fun turnScreenOn() {
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)

            val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this,null)
        }
        // Deprecated flags are required on some devices, even with API>=27
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    }
}