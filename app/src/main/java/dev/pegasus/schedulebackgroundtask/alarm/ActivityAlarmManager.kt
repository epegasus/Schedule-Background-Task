package dev.pegasus.schedulebackgroundtask.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.pegasus.schedulebackgroundtask.R
import dev.pegasus.schedulebackgroundtask.TAG
import dev.pegasus.schedulebackgroundtask.databinding.ActivityAlarmManagerBinding
import dev.pegasus.schedulebackgroundtask.alarm.receiver.AlarmReceiver
import java.util.Calendar

class ActivityAlarmManager : AppCompatActivity() {

    private val binding by lazy { ActivityAlarmManagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()

        binding.mbBack.setOnClickListener { finish() }
        binding.mbStartAlarm.setOnClickListener { checkPermission() }
    }

    private fun fullScreen() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:$packageName"))
                startActivity(intent)
                return
            }
        }
        scheduleAlarm()
    }

    private fun scheduleAlarm() {
        // Create an intent to AlarmReceiver
        val intent = Intent(this, AlarmReceiver::class.java)
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, flags)

        val triggerTime = Calendar.getInstance().apply {
            add(Calendar.SECOND, 10)
        }.timeInMillis

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)

        Log.d(TAG, "scheduleAlarm: called")
    }
}