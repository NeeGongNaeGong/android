package com.ssafy.neegongnaegong.presentation.component.picker.date

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

/**
 * DatePicker
 *
 * @param modifier modifier
 * @param state DatePickerState
 * @param minMonth 최소 선택 가능 달
 * @param maxMonth 최대 선택 가능 달
 * @param onDateSelected 날짜 선택 시 호출되는 콜백
 */
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    minMonth: YearMonth = YearMonth.of(1900, 1),
    maxMonth: YearMonth = YearMonth.of(2100, 12),
    onDateSelected: (LocalDate) -> Unit,
) {
    /**
     * pageCount는 전체 달 수
     * initialPage는 전체 중 초기 선택 달의 인덱스
     */
    val pagerState =
        rememberPagerState(
            pageCount = { ChronoUnit.MONTHS.between(minMonth, maxMonth).toInt() + 1 },
            initialPage = ChronoUnit.MONTHS.between(minMonth, state.month).toInt(),
        )

    /**
     * pagerState.currentPage가 변경될 때마다
     * updateMonth
     */
    LaunchedEffect(pagerState.currentPage) {
        state.updateMonth(minMonth.plusMonths(pagerState.currentPage.toLong()))
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
            ChronoUnit.MONTHS.between(minMonth, state.month).toInt(),
        )
    }

    Column(modifier = modifier) {
        DatePickerHeader(
            modifier =
                Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
            selectedMonth = state.month,
        )
        HorizontalPager(
            modifier = Modifier.wrapContentHeight(),
            state = pagerState,
            beyondViewportPageCount = 1,
            verticalAlignment = Alignment.Top,
        ) { page ->
            key(page) {
                val displayMonth = minMonth.plusMonths(page.toLong())
                DatePickerBody(
                    modifier = Modifier.fillMaxWidth(),
                    selectedMonth = displayMonth,
                ) { date ->
                    val isSelected = state.date == date
                    DatePickerCell(
                        modifier = Modifier.fillMaxWidth(),
                        date = date,
                        isSelected = isSelected,
                        onSelected = state::updateDate,
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateRangePickerPreview() {
    val state = rememberDatePickerState()

    NeeGongNaeGongTheme {
        DatePicker(
            state = state,
            onDateSelected = {},
        )
    }
}
