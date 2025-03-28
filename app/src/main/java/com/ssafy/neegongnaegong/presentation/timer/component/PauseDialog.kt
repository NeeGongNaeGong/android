package com.ssafy.neegongnaegong.presentation.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors.Blue
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors.Peach
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun PauseDialog(
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = { onCancel.invoke() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.txt_dialog_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.txt_dialog_content),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalDivider(thickness = 0.4.dp, color = Color.Gray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(R.string.txt_dialog_pause),
                            style = Typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Peach
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    VerticalDivider(
                        thickness = 0.4.dp,
                        color = Color.Gray,
                        modifier = Modifier.height(50.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(R.string.txt_dialog_close),
                            style = Typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                }
            }
        }
    }
}
