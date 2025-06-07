package com.ssafy.neegongnaegong.presentation.timer.component.write

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun LearningWriteCancelDialog(
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = { onCancel.invoke() }) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        NeeGongNaeGongTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp),
                    ),
        ) {
            Column(
                modifier =
                    Modifier
                        .background(NeeGongNaeGongTheme.colorScheme.background)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text =
                        buildAnnotatedString {
                            append("공부기록을 ")
                            withStyle(
                                style =
                                    SpanStyle(
                                        color = NeeGongNaeGongTheme.colorScheme.blue,
                                        fontWeight = FontWeight.Bold,
                                    ),
                            ) {
                                append("취소")
                            }
                            append(" 할까요?")
                        },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "취소해도 기록은 남아서 수정할 수 있어요!",
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
                            text = "아니요",
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
                            text = "네",
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
fun LearningWriteCancelDialogPreview() {
    NeeGongNaeGongTheme {
        LearningWriteCancelDialog(
            onCancel = {},
            onDismiss = {},
            onConfirm = {},
        )
    }
}
