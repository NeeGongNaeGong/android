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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.getTextHeightDp
import com.ssafy.neegongnaegong.presentation.util.getTextWidthDp
import java.time.LocalDateTime

@Composable
fun ScheduleInfo(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    showPrefix: Boolean = true,
    onClick: (Schedule) -> Unit = {}
) {
    ScheduleInfo(
        modifier = modifier,
        showPrefix = showPrefix,
        startTime = schedule.info.startAt,
        endTime = schedule.info.endAt,
        isAllDay = schedule.info.isAllDay,
        title = schedule.info.title,
        color = Color.Transparent,
        onClick = { onClick(schedule) },
    )
}

@Composable
fun ScheduleInfo(
    modifier: Modifier = Modifier,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    isAllDay: Boolean,
    title: String,
    color: Color,
    showPrefix: Boolean = true,
    onClick: () -> Unit = {}
) {
    val isOverNight = startTime.toLocalDate() != endTime.toLocalDate()

    val prefixWidth = getTextWidthDp("00:00", NeeGongNaeGongTheme.typography.bodySmall)
    val prefixHeight = getTextHeightDp("", NeeGongNaeGongTheme.typography.bodyMedium)

    Column(modifier = modifier
        .clickable { onClick() }
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(prefixHeight)
                    .width(prefixWidth),
                contentAlignment = Alignment.Center
            ) {
                if (showPrefix) {
                    if (isAllDay || isOverNight) {
                        Icon(
                            Icons.Filled.Today,
                            tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                            contentDescription = "this is all day or overnight schedule",
                            modifier = Modifier.height(20.dp)
                        )
                    } else {
                        Text(
                            text = "%02d:%02d".format(
                                startTime.hour,
                                startTime.minute
                            ),
                            color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            style = NeeGongNaeGongTheme.typography.bodySmall
                        )
                    }

                }
            }
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(getTextHeightDp("", NeeGongNaeGongTheme.typography.bodyMedium))
                    .background(color, shape = RoundedCornerShape(100)),
            )
            Spacer(Modifier.width(5.dp))
            Column {
                Text(
                    text = title,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
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
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    style = NeeGongNaeGongTheme.typography.labelSmall
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun ScheduleInfoPreview() {
    NeeGongNaeGongTheme {
        Surface {
            Column {
                ScheduleInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NeeGongNaeGongTheme.colorScheme.background),
                    startTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = LocalDateTime.of(2024, 1, 2, 0, 0),
                    isAllDay = true,
                    title = "Test Schedule",
                    color = Color.Red
                )
                ScheduleInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NeeGongNaeGongTheme.colorScheme.background),
                    startTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = LocalDateTime.of(2024, 1, 2, 1, 0),
                    isAllDay = false,
                    title = "Test Schedule asdfasfdasdfasdfasfdasdfasdfasdfasfdasdfasfd",
                    color = Color.Red,
                    showPrefix = false,
                )
                ScheduleInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NeeGongNaeGongTheme.colorScheme.background),
                    startTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = LocalDateTime.of(2024, 1, 1, 1, 0),
                    isAllDay = false,
                    title = "Test Schedule",
                    color = Color.Red
                )
            }
        }
    }
}
