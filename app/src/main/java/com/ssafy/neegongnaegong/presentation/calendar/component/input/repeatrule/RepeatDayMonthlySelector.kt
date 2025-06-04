package com.ssafy.neegongnaegong.presentation.calendar.component.input.repeatrule

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.isRepeatDaySelected
import com.ssafy.neegongnaegong.domain.model.calendar.toggleRepeatDay
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun RepeatRuleMonthlySelector(
    modifier: Modifier = Modifier,
    repeatDay: Int,
    onChange: (Int) -> Unit
) {
    val rows = (1..31).chunked(7)
    Column(modifier) {
        rows.forEach { week ->
            Row(Modifier.fillMaxWidth()) {
                week.forEach { day ->
                    val selected = repeatDay.isRepeatDaySelected(day)
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
                                    onChange(repeatDay.toggleRepeatDay(day))
                                }
                                .border(
                                    width = 1.dp,
                                    color = if (selected) NeeGongNaeGongTheme.colorScheme.blue else Color.Transparent,
                                    shape = CircleShape,
                                )
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = day.toString(),
                                style = NeeGongNaeGongTheme.typography.labelMedium,
                                color = if (selected) NeeGongNaeGongTheme.colorScheme.blue else NeeGongNaeGongTheme.colorScheme.primaryText,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                if (week.size < 7) {
                    repeat(7 - week.size) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun RepeatRuleMonthlySelectorPreview() {
    NeeGongNaeGongTheme {
        RepeatRuleMonthlySelector(
            repeatDay = 2,
            onChange = {}
        )
    }
}
