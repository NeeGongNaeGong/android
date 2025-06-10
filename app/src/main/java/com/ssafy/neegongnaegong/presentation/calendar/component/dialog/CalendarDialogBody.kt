package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate

@Composable
fun CalendarDialogBody(
    modifier: Modifier = Modifier,
    date: LocalDate,
    content: @Composable (LocalDate) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = NeeGongNaeGongTheme.colorScheme.background,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalendarDialogHeader(date = date)
            HorizontalDivider()
            Box(modifier = Modifier.weight(1f)) {
                content(date)
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun CalendarDialogBodyPreview(modifier: Modifier = Modifier) {
    NeeGongNaeGongTheme {
        CalendarDialogBody(date = LocalDate.now())
    }
}
