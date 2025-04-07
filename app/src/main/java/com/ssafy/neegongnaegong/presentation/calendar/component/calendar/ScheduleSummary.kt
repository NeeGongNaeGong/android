package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import java.time.LocalDateTime

@Composable
fun ScheduleSummary(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = Color.Transparent
) {
    Box(
        modifier = modifier.background(color, RoundedCornerShape(5.dp))
    ) {
        Text(
            modifier = modifier.padding(2.dp),
            text = title,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview
@Composable
fun ScheduleSummaryPreview() {
    val schedules = listOf(
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 1,
            info = ScheduleInfo(
                title = "Meeting",
                content = "Meeting",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1)
            )
        ),
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 2,
            info = ScheduleInfo(
                title = "Lunch",
                content = "Lunch",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1)
            )
        ),
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        schedules.take(3).forEach { schedule ->
            ScheduleSummary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                title = schedule.info.title,
                color = Color.Gray
            )
        }
    }
}
