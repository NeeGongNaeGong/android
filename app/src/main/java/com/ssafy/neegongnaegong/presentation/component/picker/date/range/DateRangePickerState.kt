package com.ssafy.neegongnaegong.presentation.component.picker.date.range

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDate

/**
 * startDate는 endDate 이후의 날짜 일수 없습니다.
 *
 * @param startDate 시작 날짜
 * @param endDate 종료 날짜
 * @return DateRangePickerState
 */
@Composable
fun rememberDateRangePickerState(
    startDate: LocalDate = LocalDate.now(),
    endDate: LocalDate = startDate,
    autoFocus: Boolean = true,
): DateRangePickerState {
    require(startDate <= endDate) {
        "startDate must be before or equal to endDate"
    }

    return remember { DateRangePickerState(startDate, endDate, autoFocus) }
}

/**
 * DateRangePicker의 상태를 관리합니다.
 *
 * @param startDate 시작 날짜
 * @param endDate 종료 날짜
 */
@Stable
class DateRangePickerState internal constructor(
    startDate: LocalDate,
    endDate: LocalDate,
    private val autoFocus: Boolean,
) {
    /**
     * 시작 날짜
     */
    var startDate by mutableStateOf(startDate)
        private set

    /**
     * 종료 날짜
     */
    var endDate by mutableStateOf(endDate)
        private set

    /**
     * 현재 Focus 상태
     */
    var focus by mutableStateOf(Focus.Start)
        private set

    /**
     * `focus`를 `Start`로 변경합니다.
     */
    fun focusOnStart() {
        focus = Focus.Start
    }

    /**
     * `focus`를 `End`로 변경합니다.
     */
    fun focusOnEnd() {
        focus = Focus.End
    }

    /**
     * `focus`에 따라 `StartDate`를 업데이트 할지, `EndDate`를 업데이트 할지 결정합니다.
     *
     * @param date 업데이트 할 날짜
     */
    fun updateDate(date: LocalDate) {
        when (focus) {
            Focus.Start -> updateStartDate(date)
            Focus.End -> updateEndDate(date)
        }
    }

    /**
     * `startDate`를 업데이트 합니다.
     * 만약 `endDate`가 `startDate`보다 작다면 `endDate`도 업데이트 합니다.
     * 만약 `autoFocus`가 true라면 `focus`를 `End`로 변경합니다.
     *
     * @param date 업데이트 할 날짜
     */
    fun updateStartDate(date: LocalDate) {
        startDate = date
        if (endDate < date) endDate = date
        if (autoFocus) focusOnEnd()
    }

    /**
     * `endDate`를 업데이트 합니다.
     * 만약 `startDate`가 `endDate`보다 크다면 `startDate`도 업데이트 합니다.
     * 만약 `autoFocus`가 true라면 `focus`를 `Start`로 변경합니다.
     *
     * @param date 업데이트 할 날짜
     */
    fun updateEndDate(date: LocalDate) {
        endDate = date
        if (startDate > date) startDate = date
        if (autoFocus) focusOnStart()
    }

    enum class Focus {
        Start,
        End
    }
}