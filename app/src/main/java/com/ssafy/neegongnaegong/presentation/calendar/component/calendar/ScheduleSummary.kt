package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.data.model.calendar.Schedule
import com.ssafy.neegongnaegong.data.model.calendar.ScheduleType
import java.time.LocalDateTime

@Composable
fun ScheduleSummary(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
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
            title = "Meeting",
            content = "Meeting",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusHours(1)
        ),
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 2,
            title = "Lunch",
            content = "Lunch",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusHours(1)
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
                title = schedule.title
            )
        }
    }
}