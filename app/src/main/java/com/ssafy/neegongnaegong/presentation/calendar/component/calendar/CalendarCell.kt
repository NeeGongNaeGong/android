package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarCell(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean = false,
    onSelect: (LocalDate) -> Unit = { },
    content: @Composable () -> Unit = { },
) {
    CalendarCell(
        modifier = modifier,
        date = date.dayOfMonth,
        isSelected = isSelected,
        isToday = date == LocalDate.now(),
        dateColor = when {
            date == LocalDate.now() -> MaterialTheme.colorScheme.surface
            else -> date.dayOfWeek.color
        },
        onSelect = { onSelect(date) },
        content = content,
    )
}

@Composable
fun CalendarCell(
    modifier: Modifier = Modifier,
    date: Int,
    isSelected: Boolean = false,
    isToday: Boolean = false,
    dateColor: Color = MaterialTheme.colorScheme.onBackground,
    onSelect: () -> Unit = {},
    content: @Composable () -> Unit = { }
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                if (isSelected) BorderStroke(
                    0.5.dp,
                    MaterialTheme.colorScheme.primary
                ) else BorderStroke(0.dp, Color.Transparent),
                RoundedCornerShape(5.dp)
            )
            .clickable { onSelect() }
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = dateColor,
                    textAlign = TextAlign.Center
                )
            }
            content()
        }

    }
}

@Preview
@Composable
fun CalendarCellPreview() {
    NeeGongNaeGongTheme {
        Row(
            modifier = Modifier
                .background(Color.White)
                .height(100.dp)
        ) {
            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 1,
                dateColor = DayOfWeek.SUNDAY.color
            )

            // Regular day
            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 2,
                dateColor = DayOfWeek.MONDAY.color
            )

            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 3,
                dateColor = DayOfWeek.TUESDAY.color
            )

            // Selected day
            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 4,
                dateColor = DayOfWeek.THURSDAY.color
            )

            // Today
            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 5,
                isToday = true,
                dateColor = DayOfWeek.FRIDAY.color
            )

            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 6,
                dateColor = DayOfWeek.SATURDAY.color
            )

            // Holiday
            CalendarCell(
                modifier = Modifier.weight(1f),
                date = 7,
            )
        }
    }
}
