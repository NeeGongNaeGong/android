package com.ssafy.neegongnaegong.presentation.calendar.component.input.repeatrule.radio

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun RepeatRuleRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            modifier = Modifier.padding(start = 2.dp),
            selected = selected,
            onClick = onClick,
            colors = RadioButtonColors(
                selectedColor = NeeGongNaeGongTheme.colorScheme.blue,
                unselectedColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                disabledSelectedColor = NeeGongNaeGongTheme.colorScheme.blue,
                disabledUnselectedColor = NeeGongNaeGongTheme.colorScheme.primaryText
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        content()
    }
}