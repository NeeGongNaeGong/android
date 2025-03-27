package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.color
import java.time.LocalDate

@Composable
fun DatePickerCell(
    modifier: Modifier = Modifier,
    date: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
    onSelect: (LocalDate) -> Unit = { },
) {
    val isSelected =
        date.isAfter(startDate) && date.isBefore(endDate) || date == startDate || date == endDate

    DatePickerCell(
        modifier = modifier,
        date = date.dayOfMonth,
        isSelected = isSelected,
        isLeftEdge = date == startDate,
        isRightEdge = date == endDate,
        dateColor = when {
            isSelected -> MaterialTheme.colorScheme.surface
            else -> date.dayOfWeek.color
        },
        onSelect = { onSelect(date) },
    )
}

@Composable
fun DatePickerCell(
    modifier: Modifier = Modifier,
    date: Int,
    isSelected: Boolean = false,
    isLeftEdge: Boolean = false,
    isRightEdge: Boolean = false,
    dateColor: Color = MaterialTheme.colorScheme.onBackground,
    onSelect: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelected && !isLeftEdge) MaterialTheme.colorScheme.primary else Color.Transparent)
                .padding(4.dp),
            text = "",
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            modifier = Modifier
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = if (!isLeftEdge) 0.dp else 10.dp,
                        bottomStart = if (!isLeftEdge) 0.dp else 10.dp,
                        topEnd = if (!isRightEdge) 0.dp else 10.dp,
                        bottomEnd = if (!isRightEdge) 0.dp else 10.dp
                    )
                )
                .padding(4.dp),
            text = date.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = dateColor,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelected && !isRightEdge) MaterialTheme.colorScheme.primary else Color.Transparent)
                .padding(4.dp),
            text = "",
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview
@Composable
private fun DatePickerCellPreview() {
    DatePickerCell(
        date = 1,
        dateColor = Color.Black,
        isSelected = true,
        isLeftEdge = true,
        isRightEdge = false,
        onSelect = { },
    )
}
