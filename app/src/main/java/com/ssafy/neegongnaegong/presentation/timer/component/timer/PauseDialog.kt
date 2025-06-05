package com.ssafy.neegongnaegong.presentation.timer.component.timer

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
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun PauseDialog(
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = { onCancel.invoke() }) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(NeeGongNaeGongTheme.colorScheme.background, shape = RoundedCornerShape(12.dp)),
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .background(NeeGongNaeGongTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.txt_dialog_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.txt_dialog_content),
                    fontSize = 14.sp,
                    color = NeeGongNaeGongTheme.colorScheme.gray4,
                )

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalDivider(thickness = 0.4.dp, color = Color.Gray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            stringResource(R.string.txt_dialog_pause),
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = NeeGongNaeGongTheme.colorScheme.peach,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    VerticalDivider(
                        thickness = 0.4.dp,
                        color = Color.Gray,
                        modifier = Modifier.height(50.dp),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            stringResource(R.string.txt_dialog_close),
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = NeeGongNaeGongTheme.colorScheme.blue,
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PauseDialogPreview() {
    PauseDialog(
        onCancel = {},
        onDismiss = {},
        onConfirm = {},
    )
}
