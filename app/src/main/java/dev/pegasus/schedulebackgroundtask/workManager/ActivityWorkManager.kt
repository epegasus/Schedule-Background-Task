package dev.pegasus.schedulebackgroundtask.workManager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.pegasus.schedulebackgroundtask.R
import dev.pegasus.schedulebackgroundtask.databinding.ActivityWorkManagerBinding
import dev.pegasus.schedulebackgroundtask.workManager.worker.MyWorker
import java.util.concurrent.TimeUnit

class ActivityWorkManager : AppCompatActivity() {

    private val binding by lazy { ActivityWorkManagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()

        binding.mbBack.setOnClickListener { finish() }
        binding.mbStartWork.setOnClickListener { schedulePeriodicWork() }
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

    private fun schedulePeriodicWork() {
        // Define constraints for the work (optional)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create the periodic work request
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        // Schedule the work
        WorkManager.getInstance(this).enqueue(periodicWorkRequest)
    }
}