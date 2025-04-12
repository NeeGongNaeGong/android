package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarHeader
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = LocalDate.now(),
    initialMonth: YearMonth = YearMonth.now(),
    minMonth: YearMonth = YearMonth.of(1900, 1),
    maxMonth: YearMonth = YearMonth.of(2100, 12),
    onDateSelected: (LocalDate) -> Unit = {},
    startDate: LocalDate? = LocalDate.now(),
    endDate: LocalDate? = LocalDate.now(),
) {
    val pagerState = rememberPagerState(
        pageCount = { ChronoUnit.MONTHS.between(minMonth, maxMonth).toInt() + 1 },
        initialPage = ChronoUnit.MONTHS.between(minMonth, initialMonth).toInt(),
    )
    var currentPage by remember { mutableIntStateOf(pagerState.currentPage) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedDate by remember { mutableStateOf(initialDate) }

    LaunchedEffect(pagerState.currentPage) {
        selectedMonth = minMonth.plusMonths(pagerState.currentPage.toLong())
        currentPage = pagerState.currentPage
    }

    LaunchedEffect(selectedDate) {
        if (selectedDate == null) return@LaunchedEffect
        if (selectedMonth != YearMonth.from(selectedDate)) {
            selectedMonth = YearMonth.from(selectedDate)
            pagerState.animateScrollToPage(
                ChronoUnit.MONTHS.between(minMonth, selectedMonth).toInt()
            )
        }
        selectedDate?.let { onDateSelected(it) }
    }

    Column(modifier = modifier) {
        CalendarHeader(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            selectedMonth = selectedMonth
        )
        HorizontalPager(
            modifier = Modifier.wrapContentHeight(),
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            key(page) {
                val displayedMonth by remember(page) {
                    mutableStateOf(minMonth.plusMonths(page.toLong()))
                }

                DatePickerBody(
                    modifier = Modifier.fillMaxWidth(),
                    selectedMonth = displayedMonth,
                    startDate = startDate,
                    endDate = endDate,
                    onDateSelected = { date -> selectedDate = date },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDatePicker() {
    DateRangePicker()
}