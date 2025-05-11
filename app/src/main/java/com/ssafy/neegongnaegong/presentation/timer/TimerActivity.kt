package com.ssafy.neegongnaegong.presentation.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.presentation.component.snackbar.NeeGongNaeGongSnackbarHost
import com.ssafy.neegongnaegong.presentation.timer.learning.LearningRecordWriteRoute
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.AccelerometerHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerActivity : ComponentActivity() {
    private val viewModel: TimerViewModel by viewModels()
    private lateinit var accelerometerHelper: AccelerometerHelper

    private val screenReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent,
            ) {
                when (intent.action) {
                    Intent.ACTION_SCREEN_OFF -> viewModel.setEvent(TimerContract.Event.OffScreen)
                    Intent.ACTION_SCREEN_ON -> viewModel.setEvent(TimerContract.Event.OnScreen)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accelerometerHelper =
            AccelerometerHelper(this) { isBack ->
                viewModel.setEvent(TimerContract.Event.OnFlip(isBack))
            }
        accelerometerHelper.register()

        val filter =
            IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction(Intent.ACTION_SCREEN_ON)
            }

        registerReceiver(screenReceiver, filter)

        setContent {
            NeeGongNaeGongTheme {
                Scaffold(snackbarHost = { NeeGongNaeGongSnackbarHost() }) { innerPadding ->
                    LearningRoute(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        onCloseActivity = {
                            val resultIntent =
                                Intent().apply {
                                    putExtra("needRefreshRecordList", true)
                                }
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        },
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        accelerometerHelper.unregister()
        unregisterReceiver(screenReceiver)
    }
}

@Composable
fun LearningRoute(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel,
    onCloseActivity: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isTimerScreen) {
        TimerRoute(
            modifier = modifier,
            onCloseActivity = onCloseActivity,
        )
    } else {
        LearningRecordWriteRoute(
            modifier = modifier,
            learningRecord = uiState.learningRecord,
            onCloseActivity = onCloseActivity,
        )
    }
}
