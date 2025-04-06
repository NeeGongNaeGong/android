package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.component.button.SuspendIconButton
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate

@Composable
fun ScheduleInput(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    isLoading: Boolean = false,
    onSubmit: (LocalDate, String) -> Unit = { _, _ -> },
) {
    var textState by remember { mutableStateOf("") }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(100),
            singleLine = true,
            value = textState,
            onValueChange = { textState = it },
            textStyle = MaterialTheme.typography.bodySmall,
            placeholder = {
                Text(
                    text = "%02d월 %02d일 일정 추가".format(
                        selectedDate.monthValue,
                        selectedDate.dayOfMonth
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        SuspendIconButton(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ),
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

@Preview
@Composable
fun ScheduleInputPreview(modifier: Modifier = Modifier) {
    NeeGongNaeGongTheme(dynamicColor = false) {
        ScheduleInput(
            selectedDate = LocalDate.now(),
        )
    }
}
