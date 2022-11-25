package com.example.wakelockjavaapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import kotlinx.coroutines.*

class MainActivity2 : AppCompatActivity() {

    val myviewmodel : Myviewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        logPrint("finf",myviewmodel.toString())

        supportFragmentManager.beginTransaction().replace(R.id.fragment2,BlankFragment()).commitNow()

       // val textview = findViewById<TextView>(R.id.textvieww)

        //textview.text = myviewmodel.stateFlow.value

        logPrint("fifnfv",myviewmodel.stateFlow.value.toString())

        CoroutineScope(Dispatchers.Main).launch{
            delay(2000)
            myviewmodel._mutablestateFlow.value = "After 2 Seconds"
            myviewmodel._mutablesharedFlow.emit("Hello bro")
          //  logPrint("fifnfv",myviewmodel.stateFlow.value.toString())
        }


    }


    fun logPrint(tag:String,msg:String){
        Log.d(tag,msg)
    }
}