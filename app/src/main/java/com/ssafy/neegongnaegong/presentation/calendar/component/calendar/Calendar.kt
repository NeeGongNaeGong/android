package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onMonthChanged: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    dateContent: @Composable (LocalDate) -> Unit = {},
) {
    /**
     * pageCount는 전체 달 수
     * initialPage는 전체 중 초기 선택 달의 인덱스
     */
    val pagerState = rememberPagerState(
        pageCount = { ChronoUnit.MONTHS.between(state.minMonth, state.maxMonth).toInt() + 1 },
        initialPage = ChronoUnit.MONTHS.between(state.minMonth, state.month).toInt(),
    )

    /**
     * pagerState.currentPage가 변경될 때마다
     * updateMonth
     */
    LaunchedEffect(pagerState.currentPage) {
        state.updateMonth(state.minMonth.plusMonths(pagerState.currentPage.toLong()))
    }

    /**
     * state.date가 변경될 때마다
     * onDateSelected 호출
     */
    LaunchedEffect(state.date) {
        onDateSelected(state.date)
    }

    /**
     * state.month가 변경될 때마다
     * 해당하는 달로 pagerState.currentPage 업데이트
     */
    LaunchedEffect(state.month) {
        pagerState.animateScrollToPage(
            ChronoUnit.MONTHS.between(state.minMonth, state.month).toInt()
        )
        onMonthChanged(state.month)
    }

    Column(modifier = modifier) {
        CalendarHeader(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            selectedMonth = state.month
        )
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            key(page) {
                val displayedMonth = state.minMonth.plusMonths(page.toLong())
                CalendarBody(
                    modifier = Modifier.fillMaxSize(),
                    selectedMonth = displayedMonth,
                    selectedDate = state.date,
                    onDateSelected = state::updateDate,
                    dateContent = dateContent
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarPreview() {
    val state = rememberCalendarState()
    NeeGongNaeGongTheme {
        Calendar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            state = state,
            onDateSelected = {},
            onMonthChanged = {}
        )
    }
}
