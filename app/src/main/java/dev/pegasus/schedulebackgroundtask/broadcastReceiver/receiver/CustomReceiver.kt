package dev.pegasus.schedulebackgroundtask.broadcastReceiver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val text = intent.getStringExtra("dev.pegasus.EXTRA_TEXT") ?: "Null"
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}