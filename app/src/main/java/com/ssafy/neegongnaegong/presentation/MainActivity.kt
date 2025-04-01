package com.ssafy.neegongnaegong.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRule
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.calendar.edit.ScheduleEditRoute
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeeGongNaeGongTheme(dynamicColor = false) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                ) { paddingValues ->
                    ScheduleEditRoute(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        schedule = Schedule(
                            type = ScheduleType.PERSONAL,
                            id = 0L,
                            info = ScheduleInfo(
                                title = "",
                                content = "",
                                startDate = LocalDateTime.now(),
                                endDate = LocalDateTime.now(),
                                isAllDay = false,
                                location = "",
                                repeatRule = RepeatRule(
                                    id = 0L,
                                    info = RepeatRuleInfo(
                                        repeatType = RepeatType.DAILY,
                                        repeatInterval = 2,
                                        repeatDay = 0,
                                        endDate = null
                                    )
                                )
                            )
                        ),
                        popBackStack = {}
                    )
                }
            }
        }
    }
}
