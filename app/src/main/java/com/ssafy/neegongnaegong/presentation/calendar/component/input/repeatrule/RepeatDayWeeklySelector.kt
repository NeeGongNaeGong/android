package com.ssafy.neegongnaegong.presentation.calendar.component.input.repeatrule

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.isRepeatDaySelected
import com.ssafy.neegongnaegong.domain.model.calendar.toggleRepeatDay
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.color
import com.ssafy.neegongnaegong.domain.model.calendar.dayOfWeekOrder
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun RepeatDayWeeklySelector(
    modifier: Modifier = Modifier,
    repeatDay: Int,
    onChange: (Int) -> Unit
) {
    Row(modifier) {
        dayOfWeekOrder.forEach { dayOfWeek ->
            val selected = repeatDay.isRepeatDaySelected(dayOfWeek.value)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            onChange(repeatDay.toggleRepeatDay(dayOfWeek.value))
                        }
                        .border(
                            width = 1.dp,
                            color = if (selected) NeeGongNaeGongTheme.colorScheme.blue else Color.Transparent,
                            shape = CircleShape,
                        )
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                        color = if (selected) NeeGongNaeGongTheme.colorScheme.blue else dayOfWeek.color,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun RepeatDayWeeklySelectorPreview() {
    var repeatDay by remember { mutableIntStateOf(6) }
    NeeGongNaeGongTheme {
        RepeatDayWeeklySelector(
            repeatDay = repeatDay,
            onChange = { repeatDay = it }
        )
    }
}
