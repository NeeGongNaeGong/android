package com.ssafy.neegongnaegong.presentation.component.picker.date

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun rememberDatePickerState(
    initialDate: LocalDate = LocalDate.now()
): DatePickerState {
    return remember { DatePickerState(initialDate) }
}

@Stable
class DatePickerState(
    initialDate: LocalDate
) {
    var date by mutableStateOf(initialDate)
        private set
    var month by mutableStateOf(YearMonth.from(initialDate))
        private set

    fun updateDate(newDate: LocalDate) {
        date = newDate
        YearMonth.from(newDate).let {
            if (month != it) month = it
        }
    }

    fun updateMonth(newMonth: YearMonth) {
        if (month != newMonth) {
            month = newMonth
            date = newMonth.atDay(1)
        }
    }
}
