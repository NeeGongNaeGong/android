package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.color
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarDialogHeader(
    modifier: Modifier = Modifier,
    date: LocalDate,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "${date.dayOfMonth}",
            color = date.dayOfWeek.color,
            style = NeeGongNaeGongTheme.typography.titleLarge,
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN),
            color = date.dayOfWeek.color,
            style = NeeGongNaeGongTheme.typography.titleSmall,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun CalendarDialogHeaderPreview() {
    NeeGongNaeGongTheme {
        CalendarDialogHeader(date = LocalDate.now())
    }
}
