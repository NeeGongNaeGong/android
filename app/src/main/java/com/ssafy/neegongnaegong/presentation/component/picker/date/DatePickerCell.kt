package com.ssafy.neegongnaegong.presentation.component.picker.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.ssafy.neegongnaegong.presentation.util.color
import java.time.LocalDate

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
            isSelected -> MaterialTheme.colorScheme.surface
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
    dateColor: Color = MaterialTheme.colorScheme.onBackground,
    onSelected: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = date.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = dateColor,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun DatePickerCellPreview() {
    Row {
        repeat(7) {
            val date = it + 1
            DatePickerCell(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                date = date,
                dateColor = Color.Black,
                isSelected = date == 3,
                onSelected = { },
            )
        }
    }
}
