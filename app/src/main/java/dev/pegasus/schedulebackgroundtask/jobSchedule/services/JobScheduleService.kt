package dev.pegasus.schedulebackgroundtask.jobSchedule.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import dev.pegasus.schedulebackgroundtask.TAG

/**
 *   Developer: Sohaib Ahmed
 *   Date: 11/12/2024
 *   Profile:
 *     -> github.com/epegasus
 *     -> linkedin.com/in/epegasus
 */

class JobScheduleService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob: Started")
        Thread {
            Thread.sleep(3000) // 3-second delay
            Log.d(TAG, "Job completed!")
            jobFinished(params, false) // Indicate job is done
        }.start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job stopped prematurely. (Retry the job if it fails or is stopped)")
        return true
    }
}