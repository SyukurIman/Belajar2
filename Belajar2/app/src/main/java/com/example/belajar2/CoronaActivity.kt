package com.example.belajar2

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.belajar2.Alarm.AlarmReceiver
import com.example.belajar2.Alarm.DataPickerFragment
import com.example.belajar2.Alarm.TimePickerFragment
import com.example.belajar2.BroadcastSms.PermissionManager
import com.example.belajar2.MenuService.DownloadService
import com.example.belajar2.MenuService.MyBoundService
import com.example.belajar2.MenuService.MyIntentService
import com.example.belajar2.MenuService.MyService
import kotlinx.android.synthetic.main.activity_corona.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CoronaActivity : AppCompatActivity(), View.OnClickListener, DataPickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener{

    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService
    private lateinit var downloadReceiver: BroadcastReceiver
    private val mServiceConnection = object : ServiceConnection{

        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }
    }
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        //Async
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
        private const val LOG_ASYNC = "DemoAsync"

        //BroadCast Sms
        private const val SMS_REQUEST_CODE = 101
        const val ACTION_DOWNLOAD_STATUS = "download_status"

        //Alarm
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"

        //Weather
        private const val JOB_ID = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona)

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING

        GlobalScope.launch(Dispatchers.IO){
            //background thread
            val input = INPUT_STRING

            var output: String? = null

            Log.d(LOG_ASYNC, "Status : doInBackground")
            try {
                // Input Stringnya
                output = "$input Selamat Datang"

                delay(2000)

                //Pindah ke Main Thread untuk Update
                withContext(Dispatchers.Main) {
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }
            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
        }

        //Download Status
        downloadReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(DownloadService.TAG, "Download Selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)

        registerReceiver(downloadReceiver, downloadIntentFilter)

        btn_start_service.setOnClickListener(this)
        btn_start_intent_service.setOnClickListener(this)
        btn_start_bound_service.setOnClickListener(this)
        btn_stop_bound_service.setOnClickListener(this)

        btn_permission.setOnClickListener(this)
        btn_download.setOnClickListener(this)

        btn_once_date.setOnClickListener(this)
        btn_once_time.setOnClickListener(this)
        btn_set_once_alarm.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()

        btn_repeating_time.setOnClickListener(this)
        btn_set_repeating_alarm.setOnClickListener(this)
        btn_cancel_repeating_alarm.setOnClickListener(this)

        btn_start.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start_service -> {
                val mStartServiceIntent = Intent(this@CoronaActivity, MyService::class.java)
                startService(mStartServiceIntent)
            }

            R.id.btn_start_intent_service ->{
                val mStartIntentService = Intent(this@CoronaActivity, MyIntentService::class.java)
                mStartIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000L)
                startService(mStartIntentService)
            }

            R.id.btn_start_bound_service ->{
                val mBoundServiceIntent = Intent(this@CoronaActivity, MyBoundService::class.java)
                bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_stop_bound_service ->{
                unbindService(mServiceConnection)
            }

            R.id.btn_permission -> PermissionManager.check(this, android.Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE)

            R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }

            R.id.btn_once_date -> {
                val dataPickerFragment = DataPickerFragment()
                dataPickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }

            R.id.btn_once_time -> {
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }

            R.id.btn_set_once_alarm -> {
                val onceDate = tv_once_date.text.toString()
                val onceTime = tv_once_time.text.toString()
                val onceMessage = edt_once_message.text.toString()

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                        onceDate,
                        onceTime,
                        onceMessage)
            }
            R.id.btn_repeating_time -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)

            }

            R.id.btn_set_repeating_alarm -> {
                val repeatTime = tv_repeating_time.text.toString()
                val repeatMessage = edt_repeating_message.text.toString()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage)
            }

            R.id.btn_cancel_repeating_alarm -> alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)

            R.id.btn_start -> startJob()

            R.id.btn_cancel -> cancelJob()
        }
    }

    private fun startJob() {

        if (isJobRunning(this)) {
            Toast.makeText(this, "Job Service is alreddy scheduled", Toast.LENGTH_SHORT).show()
            return
        }

        val mServiceComponent = ComponentName(this, GetCurrentWeatherJobService::class.java)

        val builder = JobInfo.Builder(JOB_ID, mServiceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        builder.setRequiresDeviceIdle(false)
        builder.setRequiresCharging(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(900000)
        } else {
            builder.setPeriodic(180000)
        }

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(builder.build())
        Toast.makeText(this, "Job Service Started", Toast.LENGTH_SHORT).show()
    }

    private fun isJobRunning(context: Context): Boolean {
        var isScheduled = false

        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        for (jobInfo in scheduler.allPendingJobs) {
            if (jobInfo.id == JOB_ID) {
                isScheduled = true
                break
            }
        }

        return isScheduled
    }

    private fun cancelJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(JOB_ID)
        Toast.makeText(this, "Job Service Canceled", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
        unregisterReceiver(downloadReceiver)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this, "Sms Receiver Permission Diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, " Sms Receiver Permission Ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //AlarmSet
    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        tv_once_date.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_ONCE_TAG -> tv_once_time.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> tv_repeating_time.text = dateFormat.format(calendar.time)
            else -> {

           }
        }

    }
}