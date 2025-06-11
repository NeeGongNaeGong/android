package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.toDisplayText
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTypeSelectDialog(
    modifier: Modifier = Modifier,
    repeatType: RepeatType?,
    onDismissRequest: () -> Unit,
    onDeleteTypeSelected: (DeleteType) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = NeeGongNaeGongTheme.colorScheme.gray1,
        dragHandle = { },
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                text = "이 일정을 삭제할까요?",
                style = NeeGongNaeGongTheme.typography.titleSmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )

            Spacer(modifier.padding(top = 16.dp))

            if (repeatType != null) {
                HorizontalDivider(
                    color = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.2f),
                )

                Spacer(modifier.padding(top = 12.dp))

                DeleteType.entries.forEach {
                    Text(
                        modifier =
                            Modifier
                                .padding(vertical = 12.dp)
                                .clickable { onDeleteTypeSelected(it) }
                                .fillMaxWidth(),
                        text = it.toDisplayText(),
                        style = NeeGongNaeGongTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            "취소",
                            style = NeeGongNaeGongTheme.typography.bodyMedium,
                            color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        )
                    }
                    TextButton(
                        onClick = { onDeleteTypeSelected(DeleteType.ALL) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            "삭제",
                            style = NeeGongNaeGongTheme.typography.bodyMedium,
                            color = NeeGongNaeGongTheme.colorScheme.peach,
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DeleteTypeSelectDialogPreview() {
    NeeGongNaeGongTheme {
        DeleteTypeSelectDialog(
            repeatType = null,
            onDismissRequest = {},
            onDeleteTypeSelected = {},
        )
    }
}
