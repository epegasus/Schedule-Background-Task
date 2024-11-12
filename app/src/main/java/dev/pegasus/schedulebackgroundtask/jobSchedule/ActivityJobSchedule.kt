package dev.pegasus.schedulebackgroundtask.jobSchedule

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.pegasus.schedulebackgroundtask.R
import dev.pegasus.schedulebackgroundtask.TAG
import dev.pegasus.schedulebackgroundtask.databinding.ActivityJobScheduleBinding
import dev.pegasus.schedulebackgroundtask.jobSchedule.services.JobScheduleService

class ActivityJobSchedule : AppCompatActivity() {

    private val binding by lazy { ActivityJobScheduleBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()

        binding.mbBack.setOnClickListener { finish() }
        binding.mbStartJob.setOnClickListener { scheduleJob() }
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

    private fun scheduleJob() {
        val componentName = ComponentName(this, JobScheduleService::class.java)

        val jobInfoBuilder = JobInfo.Builder(123, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // Requires any network
            .setPersisted(true) // Job will persist across device reboots
            .setPeriodic(1 * 30 * 1000L) // Run every 15 minutes (min period)

        // Additional settings for API 24+
        //jobInfoBuilder.setMinimumLatency(10 * 1000L) // Min delay of 10 seconds

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(jobInfoBuilder.build())
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, "Job scheduled successfully!", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Job scheduled successfully!")
        } else {
            Toast.makeText(this, "Job scheduled failed!", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Job scheduling failed.")
        }
    }
}