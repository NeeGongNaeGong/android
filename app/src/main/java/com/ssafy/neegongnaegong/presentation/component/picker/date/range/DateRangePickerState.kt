package com.ssafy.neegongnaegong.presentation.component.picker.date.range

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Composable
fun rememberDateRangePickerState(
    startDate: LocalDate = LocalDate.now(),
    endDate: LocalDate = startDate,
): DateRangePickerState {
    require(startDate <= endDate) {
        "startDate must be before or equal to endDate"
    }

    return remember { DateRangePickerState(startDate, endDate) }
}

@Stable
class DateRangePickerState internal constructor(
    startDate: LocalDate,
    endDate: LocalDate,
) {
    enum class Focus {
        None,
        Start,
        End
    }

    private var _startDate by mutableStateOf(startDate)
    val startDate: LocalDate get() = _startDate

    private var _endDate by mutableStateOf(endDate)
    val endDate: LocalDate get() = _endDate

    private var _focus by mutableStateOf(Focus.None)
    val focus: Focus get() = _focus

    fun focusOnStart() {
        _focus = Focus.Start
    }

    fun focusOnEnd() {
        _focus = Focus.End
    }

    fun clearFocus() {
        _focus = Focus.None
    }

    fun setStartDate(date: LocalDate) {
        _startDate = date
        if (_endDate < date) _endDate = date
    }

    fun setEndDate(date: LocalDate) {
        _endDate = date
        if (_startDate > date) _startDate = date
    }
}