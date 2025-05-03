package com.ssafy.neegongnaegong.presentation.component.picker.date.range

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarHeader
import com.ssafy.neegongnaegong.presentation.component.picker.date.DatePickerBody
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

/**
 * DateRangePicker
 *
 * @param modifier modifier
 * @param state DateRangePickerState
 * @param initialMonth 초기 선택 달
 * @param minMonth 최소 선택 가능 달
 * @param maxMonth 최대 선택 가능 달
 * @param onStartDateSelected 시작 날짜 선택 시 호출되는 콜백
 * @param onEndDateSelected 종료 날짜 선택 시 호출되는 콜백
 */
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    state: DateRangePickerState,
    initialMonth: YearMonth = YearMonth.now(),
    minMonth: YearMonth = YearMonth.of(1900, 1),
    maxMonth: YearMonth = YearMonth.of(2100, 12),
    onStartDateSelected: (LocalDate) -> Unit = {},
    onEndDateSelected: (LocalDate) -> Unit = {},
) {
    /**
     * pageCount는 전체 달 수
     * initialPage는 전체 중 초기 선택 달의 인덱스
     */
    val pagerState = rememberPagerState(
        pageCount = { ChronoUnit.MONTHS.between(minMonth, maxMonth).toInt() + 1 },
        initialPage = ChronoUnit.MONTHS.between(minMonth, initialMonth).toInt(),
    )

    /**
     * selectedMonth는 현재 선택된 달
     */
    var selectedMonth by remember { mutableStateOf(initialMonth) }

    /**
     * pagerState.currentPage가 변경될 때마다
     * selectedMonth 업데이트
     */
    LaunchedEffect(pagerState.currentPage) {
        selectedMonth = minMonth.plusMonths(pagerState.currentPage.toLong())
    }

    /**
     * startDate가 변경될 때마다
     * 해당하는 달로 pagerState.currentPage 업데이트
     * onStartDateSelected 호출
     */
    LaunchedEffect(state.startDate) {
        if (selectedMonth != YearMonth.from(state.startDate)) {
            pagerState.animateScrollToPage(
                ChronoUnit.MONTHS.between(minMonth, selectedMonth).toInt()
            )
        }
        onStartDateSelected(state.startDate)
    }

    /**
     * endDate가 변경될 때마다
     * 해당하는 달로 pagerState.currentPage 업데이트
     * onEndDateSelected 호출
     */
    LaunchedEffect(state.endDate) {
        if (selectedMonth != YearMonth.from(state.endDate)) {
            pagerState.animateScrollToPage(
                ChronoUnit.MONTHS.between(minMonth, selectedMonth).toInt()
            )
        }
        onEndDateSelected(state.endDate)
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
                val displayedMonth by remember {
                    mutableStateOf(minMonth.plusMonths(page.toLong()))
                }
                DatePickerBody(
                    modifier = Modifier.fillMaxWidth(),
                    selectedMonth = displayedMonth,
                ) { date ->
                    DateRangePickerCell(
                        date = date,
                        startDate = state.startDate,
                        endDate = state.endDate,
                        onSelected = state::updateDate
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateRangePickerPreview() {
    val state = rememberDateRangePickerState().apply {
        val now = LocalDate.now()
        updateDate(now)
        updateDate(now.plusDays(1))
    }
    NeeGongNaeGongTheme {
        DateRangePicker(state = state)
    }
}
