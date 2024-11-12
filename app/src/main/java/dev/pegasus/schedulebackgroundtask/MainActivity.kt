package dev.pegasus.schedulebackgroundtask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.pegasus.schedulebackgroundtask.alarm.ActivityAlarmManager
import dev.pegasus.schedulebackgroundtask.broadcastReceiver.ActivityBroadcastReceiver
import dev.pegasus.schedulebackgroundtask.databinding.ActivityMainBinding
import dev.pegasus.schedulebackgroundtask.jobSchedule.ActivityJobSchedule

const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mbBroadcastReceiver.setOnClickListener { startActivity(Intent(this, ActivityBroadcastReceiver::class.java)) }
        binding.mbAlarm.setOnClickListener { startActivity(Intent(this, ActivityAlarmManager::class.java)) }
        binding.mbJobSchedule.setOnClickListener { startActivity(Intent(this, ActivityJobSchedule::class.java)) }
    }
}