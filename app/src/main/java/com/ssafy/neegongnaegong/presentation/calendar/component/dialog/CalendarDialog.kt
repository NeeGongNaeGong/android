package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarState
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.rememberCalendarState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@Composable
fun CalendarDialog(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onMonthChanged: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable (LocalDate) -> Unit = {},
) {
    /**
     * pageCount는 전체 날짜 수
     * initialPage는 전체 중 초기 선택 날짜의 인덱스
     */
    val pagerState =
        rememberPagerState(
            pageCount = {
                ChronoUnit.DAYS.between(
                    state.minMonth.atDay(1),
                    state.maxMonth.atEndOfMonth(),
                ).toInt() + 1
            },
            initialPage = ChronoUnit.DAYS.between(state.minMonth.atDay(1), state.date).toInt(),
        )

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    /**
     * pagerState.currentPage가 변경될 때마다
     * updateMonth 호출
     */
    LaunchedEffect(pagerState.currentPage) {
        state.updateDate(state.minMonth.atDay(1).plusDays(pagerState.currentPage.toLong()))
    }

    /**
     * state.date가 변경될 때마다
     * onDateSelected 호출
     */
    LaunchedEffect(state.date) {
        pagerState.animateScrollToPage(
            ChronoUnit.DAYS.between(state.minMonth.atDay(1), state.date).toInt(),
        )
        onDateSelected(state.date)
    }

    /**
     * state.month가 변경될 때마다
     * onMonthChanged 호출
     */
    LaunchedEffect(state.month) {
        onMonthChanged(state.month)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        HorizontalCarouselPager(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .then(modifier),
            state = pagerState,
        ) { page ->
            val displayDate by remember {
                mutableStateOf(state.minMonth.atDay(1).plusDays(page.toLong()))
            }
            CalendarDialogBody(
                modifier =
                    Modifier
                        .height(screenHeight * 0.7f)
                        .fillMaxWidth(),
                date = displayDate,
                content = content,
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun CalendarDialogPreview(modifier: Modifier = Modifier) {
    val calendarState = rememberCalendarState()
    NeeGongNaeGongTheme {
        CalendarDialog(
            modifier = modifier,
            state = calendarState,
            onDateSelected = {},
            onMonthChanged = {},
            onDismissRequest = {},
        )
    }
}
