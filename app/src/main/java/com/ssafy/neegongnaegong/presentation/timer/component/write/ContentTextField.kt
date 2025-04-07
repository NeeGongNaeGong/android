package com.ssafy.neegongnaegong.presentation.timer.component.write

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun ContentTextField(
    modifier: Modifier = Modifier,
    content: String,
    onContentChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = content,
        onValueChange = onContentChanged,
        textStyle = Typography.labelLarge.copy(
            fontSize = 18.sp,
            fontFeatureSettings = "tnum",
        ),
        placeholder = {
            Text(
                text = "내용을 입력하세요",
                fontSize = 18.sp,
                color = LightColors.Gray4,
                letterSpacing = 0.2.sp,
            )
        },
        maxLines = 5,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray
        )
    )
}
