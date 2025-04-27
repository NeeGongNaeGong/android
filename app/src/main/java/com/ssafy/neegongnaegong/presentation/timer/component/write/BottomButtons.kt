package com.ssafy.neegongnaegong.presentation.timer.component.write

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun BottomButtons(
    modifier: Modifier = Modifier,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            onClick = onCancelClicked,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            border = BorderStroke(0.dp, Color.Transparent),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "취소",
                style = NeeGongNaeGongTheme.typography.titleLarge.copy(
                    fontSize = 16.sp
                )
            )
        }

        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            onClick = onConfirmClicked,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            border = BorderStroke(0.dp, Color.Transparent),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "확인",
                style = NeeGongNaeGongTheme.typography.titleLarge.copy(
                    fontSize = 16.sp
                )
            )
        }
    }
}
