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
fun TitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChanged: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = title,
        onValueChange = onTitleChanged,
        textStyle = Typography.bodySmall.copy(
            fontSize = 30.sp,
            color = LightColors.Black,
            fontFeatureSettings = "tnum",
        ),
        placeholder = {
            Text(
                text = "제목을 입력하세요",
                color = LightColors.Gray4,
                fontSize = 30.sp,
                letterSpacing = 0.5.sp,
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray
        ),
    )
}
