package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun ScheduleInfo(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    onClick: (Schedule) -> Unit = {}
) {
    ScheduleInfo(
        modifier = modifier,
        startTime = schedule.startDate,
        endTime = schedule.endDate,
        title = schedule.title,
        color = Color.Transparent,
        onClick = { onClick(schedule) },
    )
}

@Composable
fun ScheduleInfo(
    modifier: Modifier = Modifier,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    title: String,
    color: Color,
    onClick: () -> Unit = {}
) {
    val isAllDay = ChronoUnit.HOURS.between(startTime, endTime).toInt() == 24
    val isOverNight = ChronoUnit.HOURS.between(startTime, endTime).toInt() > 24

    Column(modifier = modifier
        .clickable { onClick() }
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.width(56.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isAllDay || isOverNight) {
                    Icon(
                        Icons.Filled.Today,
                        contentDescription = "this is all day or overnight schedule",
                        modifier = Modifier.height(20.dp)
                    )
                } else {
                    Text(
                        text = "%02d:%02d".format(
                            startTime.hour,
                            startTime.minute
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(20.dp)
                    .background(color, shape = RoundedCornerShape(100)),
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        Row {
            Spacer(Modifier.width(64.dp))
            Text(
                text = if (isAllDay) "하루종일"
                else if (isOverNight) "%d일 %02d:%02d - %d일 %02d:%02d".format(
                    startTime.dayOfMonth,
                    startTime.hour,
                    startTime.minute,
                    endTime.dayOfMonth,
                    endTime.hour,
                    endTime.minute
                )
                else "%02d:%02d - %02d:%02d".format(
                    startTime.hour,
                    startTime.minute,
                    endTime.hour,
                    endTime.minute
                ),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun ScheduleInfoPreview() {
    NeeGongNaeGongTheme {
        Surface {
            Column {
                ScheduleInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    startTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = LocalDateTime.of(2024, 1, 2, 0, 0),
                    title = "Test Schedule",
                    color = Color.Red
                )
                ScheduleInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    startTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = LocalDateTime.of(2024, 1, 2, 1, 0),
                    title = "Test Schedule asdfasfdasdfasdfasfdasdfasdfasdfasfdasdfasfd",
                    color = Color.Red
                )
                ScheduleInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    startTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = LocalDateTime.of(2024, 1, 1, 1, 0),
                    title = "Test Schedule",
                    color = Color.Red
                )
            }
        }
    }
}