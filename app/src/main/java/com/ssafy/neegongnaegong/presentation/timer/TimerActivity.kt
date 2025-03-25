package com.ssafy.neegongnaegong.presentation.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.AccelerometerHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerActivity : ComponentActivity() {

    private val viewModel: TimerViewModel by viewModels()
    private lateinit var accelerometerHelper: AccelerometerHelper

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_SCREEN_OFF -> viewModel.setEvent(TimerContract.Event.OffScreen)
                Intent.ACTION_SCREEN_ON -> viewModel.setEvent(TimerContract.Event.OnScreen)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accelerometerHelper = AccelerometerHelper(this) { isBack ->
            viewModel.setEvent(TimerContract.Event.OnFlip(isBack))
        }
        accelerometerHelper.register()

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
        }

        registerReceiver(screenReceiver, filter)

        setContent {
            NeeGongNaeGongTheme {
                TimerScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        accelerometerHelper.unregister()
        unregisterReceiver(screenReceiver)
    }
}