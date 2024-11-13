package dev.pegasus.schedulebackgroundtask.workManager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.pegasus.schedulebackgroundtask.TAG

/**
 *   Developer: Sohaib Ahmed
 *   Date: 11/13/2024
 *   Profile:
 *     -> github.com/epegasus
 *     -> linkedin.com/in/epegasus
 */

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d(TAG, "Periodic Work started!")

        // Simulate some background work
        Thread.sleep(3000) // Simulate work taking 3 seconds
        Log.d(TAG, "Work completed!")

        // Return success to indicate the work finished successfully
        return Result.success()
    }
}
