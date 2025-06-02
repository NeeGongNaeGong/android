package com.ssafy.neegongnaegong.presentation.component.picker.date.range

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.color
import com.ssafy.neegongnaegong.presentation.util.getTextHeightDp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun DateRangePickerCell(
    modifier: Modifier = Modifier,
    date: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
    onSelected: (LocalDate) -> Unit = { },
) {
    val isSelected =
        date.isAfter(startDate) && date.isBefore(endDate) || date == startDate || date == endDate

    DateRangePickerCell(
        modifier = modifier,
        date = date.dayOfMonth,
        isSelected = isSelected,
        isLeftEdge = date == startDate,
        isRightEdge = date == endDate,
        dateColor = when {
            isSelected -> NeeGongNaeGongTheme.colorScheme.background
            else -> date.dayOfWeek.color
        },
        onSelected = { onSelected(date) },
    )
}

@Composable
fun DateRangePickerCell(
    modifier: Modifier = Modifier,
    date: Int,
    isSelected: Boolean = false,
    isLeftEdge: Boolean = false,
    isRightEdge: Boolean = false,
    dateColor: Color = NeeGongNaeGongTheme.colorScheme.primaryText,
    onSelected: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        val height = getTextHeightDp("", NeeGongNaeGongTheme.typography.labelMedium) + 8.dp
        Box(
            Modifier
                .weight(1f)
                .background(if (!isLeftEdge && isSelected) NeeGongNaeGongTheme.colorScheme.blue else Color.Transparent)
                .height(height)
        )
        Text(
            modifier = Modifier
                .background(
                    if (isSelected) NeeGongNaeGongTheme.colorScheme.blue else Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = if (!isLeftEdge) 0.dp else 10.dp,
                        bottomStart = if (!isLeftEdge) 0.dp else 10.dp,
                        topEnd = if (!isRightEdge) 0.dp else 10.dp,
                        bottomEnd = if (!isRightEdge) 0.dp else 10.dp
                    )
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = date.toString(),
            style = NeeGongNaeGongTheme.typography.labelMedium,
            color = dateColor,
            textAlign = TextAlign.Center,
        )
        Box(
            Modifier
                .weight(1f)
                .background(if (!isRightEdge && isSelected) NeeGongNaeGongTheme.colorScheme.blue else Color.Transparent)
                .height(height)
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateRangePickerCellPreview() {
    val sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    NeeGongNaeGongTheme {
        Row {
            repeat(7) {
                val date = sunday.plusDays(it.toLong())
                DateRangePickerCell(
                    modifier = Modifier.weight(1f),
                    date = date,
                    startDate = sunday.plusDays(2),
                    endDate = sunday.plusDays(4),
                )
            }
        }
    }
}
