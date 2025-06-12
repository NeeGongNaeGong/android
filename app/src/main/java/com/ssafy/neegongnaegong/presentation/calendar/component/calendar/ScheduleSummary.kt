package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.getTextHeightDp
import java.time.LocalDateTime

@Composable
fun ScheduleSummary(
    modifier: Modifier = Modifier,
    title: String,
    isAllDay: Boolean = false,
    color: Color = Color.Transparent,
) {
    Row(
        modifier =
            modifier.background(
                if (isAllDay) color else Color.Transparent,
                RoundedCornerShape(5.dp),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isAllDay) {
            Box(
                modifier =
                    Modifier
                        .width(3.dp)
                        .height(getTextHeightDp("", NeeGongNaeGongTheme.typography.labelSmall))
                        .background(color, shape = RoundedCornerShape(100)),
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        Text(
            text = title,
            style = NeeGongNaeGongTheme.typography.labelSmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun ScheduleSummaryPreview() {
    val schedules =
        listOf(
            Schedule(
                type = ScheduleType.PERSONAL,
                id = 1,
                info =
                    ScheduleInfo(
                        title = "Meeting",
                        content = "Meeting",
                        startAt = LocalDateTime.now(),
                        endAt = LocalDateTime.now().plusHours(1),
                    ),
            ),
            Schedule(
                type = ScheduleType.PERSONAL,
                id = 2,
                info =
                    ScheduleInfo(
                        title = "Lunch",
                        content = "Lunch",
                        startAt = LocalDateTime.now(),
                        endAt = LocalDateTime.now().plusHours(1),
                    ),
            ),
        )

    NeeGongNaeGongTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            schedules.take(3).forEach { schedule ->
                ScheduleSummary(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(1.dp),
                    title = schedule.info.title,
                    color = Color.Gray,
                )
            }
        }
    }
}
