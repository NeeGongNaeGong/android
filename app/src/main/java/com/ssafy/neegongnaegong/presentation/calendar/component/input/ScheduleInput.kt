package com.ssafy.neegongnaegong.presentation.calendar.component.input

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.component.button.SuspendIconButton
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.pixelsToDp
import java.time.LocalDate

@Composable
fun ScheduleInput(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    isLoading: Boolean = false,
    onSubmit: (LocalDate, String) -> Unit = { _, _ -> },
) {
    var textState by remember { mutableStateOf("") }

    // TODO : ScheduleInput SuspendIconButton 높이 설정 다시 생각해보기
    var itemHeightPixels by remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels)

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.weight(1f).onSizeChanged { itemHeightPixels = it.height  },
            shape = RoundedCornerShape(100),
            singleLine = true,
            value = textState,
            onValueChange = { textState = it },
            textStyle = NeeGongNaeGongTheme.typography.bodySmall,
            placeholder = {
                Text(
                    text = "%02d월 %02d일 일정 추가".format(
                        selectedDate.monthValue,
                        selectedDate.dayOfMonth
                    ),
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = NeeGongNaeGongTheme.colorScheme.blue,
                unfocusedContainerColor = NeeGongNaeGongTheme.colorScheme.blue,
                disabledContainerColor = NeeGongNaeGongTheme.colorScheme.blue,
                focusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                unfocusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                disabledTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                focusedPlaceholderColor = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.6f),
                disabledPlaceholderColor = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.6f),
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        SuspendIconButton(
            modifier = Modifier.size(itemHeightDp),
            imageVector = Icons.Default.Add,
            contentDescription = "Add Schedule",
            isLoading = isLoading,
            onClick = {
                onSubmit(selectedDate, textState)
                textState = ""
            }
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun ScheduleInputPreview(modifier: Modifier = Modifier) {
    NeeGongNaeGongTheme {
        ScheduleInput(
            selectedDate = LocalDate.now(),
        )
    }
}
