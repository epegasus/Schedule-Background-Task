package dev.pegasus.schedulebackgroundtask.broadcastReceiver

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.pegasus.schedulebackgroundtask.R
import dev.pegasus.schedulebackgroundtask.broadcastReceiver.receiver.CustomReceiver
import dev.pegasus.schedulebackgroundtask.databinding.ActivityBroadcastReceiverBinding

class ActivityBroadcastReceiver : AppCompatActivity() {

    private val binding by lazy { ActivityBroadcastReceiverBinding.inflate(layoutInflater) }

    private val broadcastReceiver by lazy { CustomReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()
        registerBroadcastReceiver()

        binding.mbBack.setOnClickListener { finish() }
        binding.mbTrigger.setOnClickListener { triggerBroadcastReceiver() }
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

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("dev.pegasus.ACTION_CUSTOM_BROADCAST")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    private fun triggerBroadcastReceiver() {
        val text = binding.etText.text.toString().trim()
        val intent = Intent("dev.pegasus.ACTION_CUSTOM_BROADCAST")
        intent.putExtra("dev.pegasus.EXTRA_TEXT", text)
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}