package com.example.belajar2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_weather.*
import androidx.work.Data.Builder
import java.util.*
import java.util.concurrent.TimeUnit

class WeatherActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        btnOneTimeTask.setOnClickListener(this)
        btnPeriodicTask.setOnClickListener(this)
        btnCancelTask.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTask()
            R.id.btnCancelTask -> CancelPeriodicTask()
        }

    }

    private fun CancelPeriodicTask() {
        WorkManager.getInstance().cancelWorkById(periodicWorkRequest.id)
    }

    private fun startPeriodicTask() {
        textStatus.text = getString(R.string.status1)
        val data = Builder()
                .putString(MyWorker.EXTRA_CITY, editCity.text.toString())
                .build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15 ,TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance().enqueue(periodicWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this@WeatherActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo: WorkInfo) {
                val status = workInfo.state.name
                textStatus.append("\n"+ status)
                btnCancelTask.isEnabled = false
                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    btnCancelTask.isEnabled = true
                }
            }
        })
    }

    private fun startOneTimeTask() {
        textStatus.text = getString(R.string.status1)
        val data = Builder()
                .putString(MyWorker.EXTRA_CITY, editCity.text.toString())
                .build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance().enqueue(oneTimeWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this@WeatherActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo: WorkInfo) {
                val status = workInfo.state.name
                textStatus.append("\n" + status)
            }
        })
    }
}