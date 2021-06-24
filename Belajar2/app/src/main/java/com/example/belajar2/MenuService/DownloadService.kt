package com.example.belajar2.MenuService

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import com.example.belajar2.CoronaActivity

class DownloadService : IntentService("DownloadService") {

    companion object {
        val TAG = DownloadService::class.java.simpleName
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "Download Service dijalankan")
        if (intent != null) {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val notifyFinishIntent = Intent(CoronaActivity.ACTION_DOWNLOAD_STATUS)
            sendBroadcast(notifyFinishIntent)
        }

    }
}
