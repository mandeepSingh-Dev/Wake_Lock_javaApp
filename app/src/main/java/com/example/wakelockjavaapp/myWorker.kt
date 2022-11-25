package com.example.wakelockjavaapp

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class myWorker(val appContext: Context,params:WorkerParameters) : CoroutineWorker(appContext,params){
    override suspend fun doWork(): Result {
        for(i in 0..20)
        {
            Toast.makeText(appContext,i.toString(),Toast.LENGTH_SHORT).show()
        }
        return Result.success()
    }
}