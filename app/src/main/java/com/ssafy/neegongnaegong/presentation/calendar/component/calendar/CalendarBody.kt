package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarBody(
    modifier: Modifier = Modifier,
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    dateContent: @Composable (LocalDate) -> Unit = {},
) {
    val lastDay by remember { mutableIntStateOf(selectedMonth.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(selectedMonth.atDay(1).dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier) {
        DayOfWeek()
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            columns = GridCells.Fixed(7)
        ) {
            for (i in 1 until firstDayOfWeek) {
                item {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                    )
                }
            }
            items(days) { day ->
                val date = selectedMonth.atDay(day)
                val isSelected = remember(selectedDate) {
                    selectedDate.compareTo(date) == 0
                }
                CalendarCell(
                    modifier = Modifier.height(100.dp),
                    date = date,
                    isSelected = isSelected,
                    onSelect = onDateSelected,
                ) {
                    dateContent(date)
                }
            }
        }
    }
}

@Preview
@Composable
fun CalendarBodyPreview() {
    val currentMonth = remember { YearMonth.now() }
    val currentDate = remember { LocalDate.now() }

    NeeGongNaeGongTheme {
        CalendarBody(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            selectedMonth = currentMonth,
            selectedDate = currentDate,
            onDateSelected = { }
        )
    }
}