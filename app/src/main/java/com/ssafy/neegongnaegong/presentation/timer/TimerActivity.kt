package com.ssafy.neegongnaegong.presentation.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeeGongNaeGongTheme {
                TimerScreen()
            }
        }
    }
}
