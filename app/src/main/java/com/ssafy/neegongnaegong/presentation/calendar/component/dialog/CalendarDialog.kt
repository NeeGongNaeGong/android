package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarState
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.rememberCalendarState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate

@Composable
fun CalendarDialog(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onDismissRequest: () -> Unit,
    content: @Composable (LocalDate) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CalendarDialogHeader(modifier = Modifier, date = state.date)
            HorizontalDivider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                content(state.date)
            }
        }
    }
}

@Preview
@Composable
fun CalendarDialogPreview(modifier: Modifier = Modifier) {
    val calendarState = rememberCalendarState()
    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
            CalendarDialog(
                modifier = modifier,
                state = calendarState,
                onDismissRequest = { },
            )
        }
    }
}
