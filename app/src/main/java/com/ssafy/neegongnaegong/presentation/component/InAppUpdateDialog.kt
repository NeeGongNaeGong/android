package com.ssafy.neegongnaegong.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun InAppUpdateDialog(
    modifier: Modifier = Modifier,
    onUpdateClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        containerColor = NeeGongNaeGongTheme.colorScheme.background,
        textContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
        titleContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
        properties =
            DialogProperties(
                dismissOnBackPress = false, // 뒤로가기 버튼으로 닫기는 비허용
                dismissOnClickOutside = false, // 바깥 영역 터치로 닫기는 비허용
            ),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🎉 새로운 버전이 출시됐어요!",
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        },
        text = {
            Text(
                text = "더욱 편리해진 기능을 사용해 보세요.\n지금 바로 업데이트할까요?",
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onUpdateClick,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = NeeGongNaeGongTheme.colorScheme.disable,
                    ),
            ) {
                Text(
                    "업데이트",
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = NeeGongNaeGongTheme.colorScheme.disable,
                    ),
            ) {
                Text("나중에")
            }
        },
    )
}

@NeeGongNaeGongPreviews
@Composable
fun InAppUpdateDialogPreview() {
    NeeGongNaeGongTheme {
        InAppUpdateDialog(onUpdateClick = {}, onDismiss = {})
    }
}
