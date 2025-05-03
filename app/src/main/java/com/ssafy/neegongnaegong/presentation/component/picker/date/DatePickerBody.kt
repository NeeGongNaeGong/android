package com.ssafy.neegongnaegong.presentation.component.picker.date

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth

/**
 * DatePickerBody
 *
 * @param modifier modifier
 * @param selectedMonth 선택된 달
 * @param cell 날짜 셀 컴포저블
 */
@Composable
fun DatePickerBody(
    modifier: Modifier = Modifier,
    selectedMonth: YearMonth,
    cell: @Composable (LocalDate) -> Unit
) {
    /**
     * lastDay: 선택된 달의 마지막 날짜
     * firstDayOfWeek: 선택된 달의 첫째 날의 요일
     * weeks: 선택된 달의 주 수
     */
    val lastDay by remember(selectedMonth) { mutableIntStateOf(selectedMonth.lengthOfMonth()) }
    val firstDayOfWeek by remember(selectedMonth) { mutableIntStateOf(selectedMonth.atDay(1).dayOfWeek.value % 7) }
    val weeks by remember(lastDay, firstDayOfWeek) {
        mutableIntStateOf((firstDayOfWeek + lastDay + 6) / 7)
    }

    Column(modifier = modifier) {
        Column {
            repeat(weeks) { row ->
                Row(modifier = Modifier.padding(vertical = 1.dp)) {
                    repeat(7) { column ->
                        /**
                         * 선택된 달의 첫째 날의 요일을 기준으로 날짜 계산
                         */
                        val calendarIndex = row * 7 + column
                        val dayOffset = calendarIndex - firstDayOfWeek + 1
                        val date = if (dayOffset < 1) { // 지난 달
                            val previousMonth = selectedMonth.minusMonths(1)
                            val lastDayOfPreviousMonth = previousMonth.lengthOfMonth()
                            previousMonth.atDay(lastDayOfPreviousMonth + dayOffset)
                        } else if (dayOffset > lastDay) { // 다음 달
                            val nextMonth = selectedMonth.plusMonths(1)
                            nextMonth.atDay(dayOffset - lastDay)
                        } else { // 이번 달
                            selectedMonth.atDay(dayOffset)
                        }

                        /**
                         * 지난달 혹은 다음 달 이면 반투명
                         */
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(if (dayOffset in 1..lastDay) 1f else 0.3f),
                            contentAlignment = Alignment.Center
                        ) {
                            cell(date)
                        }
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DatePickerBodyPreview() {
    NeeGongNaeGongTheme {
        DatePickerBody(
            selectedMonth = YearMonth.now(),
        ) { date ->
            DatePickerCell(
                date = date,
                isSelected = date == LocalDate.now(),
                onSelected = {},
            )
        }
    }
}
