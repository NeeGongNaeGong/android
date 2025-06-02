package com.ssafy.neegongnaegong.presentation.component.picker.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.color
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun DatePickerCell(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    onSelected: (LocalDate) -> Unit = { },
) {
    DatePickerCell(
        modifier = modifier,
        date = date.dayOfMonth,
        isSelected = isSelected,
        dateColor = when {
            isSelected -> NeeGongNaeGongTheme.colorScheme.background
            else -> date.dayOfWeek.color
        },
        onSelected = { onSelected(date) },
    )
}

@Composable
fun DatePickerCell(
    modifier: Modifier = Modifier,
    date: Int,
    isSelected: Boolean = false,
    dateColor: Color = NeeGongNaeGongTheme.colorScheme.primaryText,
    onSelected: () -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) NeeGongNaeGongTheme.colorScheme.blue else Color.Transparent,
                )
                .clickable { onSelected() }
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = date.toString(),
            style = NeeGongNaeGongTheme.typography.labelMedium,
            color = dateColor,
            textAlign = TextAlign.Center,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DatePickerCellPreview() {
    val sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))
    NeeGongNaeGongTheme {
        Row {
            repeat(7) {
                DatePickerCell(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    date = sunday.plusDays(it.toLong()),
                    isSelected = it == 3,
                    onSelected = { },
                )
            }
        }
    }
}
