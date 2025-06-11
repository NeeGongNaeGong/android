package com.ssafy.neegongnaegong.presentation.group.user.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserReportData
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDialog(
    user: UserUiModel,
    onDismiss: () -> Unit,
    onReport: (UserReportData) -> Unit,
) {
    var selectedReason by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val reportReasons =
        listOf(
            "성적인 이미지",
            "불쾌함을 유발하는 이미지",
            "폭력적인 이미지",
            "부적절한 닉네임",
        )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${user.nickname} 신고 ",
                    style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 24.sp),
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    modifier = Modifier.weight(1f, fill = false),
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_siren),
                    contentDescription = "신고 아이콘",
                    tint = Color.Red,
                )
            }
        },
        text = {
            Column {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "신고 사유를 선택해주세요",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(NeeGongNaeGongTheme.colorScheme.background),
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        modifier =
                            Modifier
                                .menuAnchor(
                                    type = MenuAnchorType.PrimaryNotEditable,
                                    enabled = true,
                                ).fillMaxWidth(),
                        value = selectedReason,
                        onValueChange = { },
                        readOnly = true,
                        label = {
                            Text(
                                "신고 사유",
                                style = MaterialTheme.typography.bodyMedium,
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors =
                            TextFieldDefaults.colors(
                                focusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                focusedContainerColor = NeeGongNaeGongTheme.colorScheme.background,
                                unfocusedContainerColor = NeeGongNaeGongTheme.colorScheme.background,
                            ),
                    )

                    ExposedDropdownMenu(
                        modifier =
                            Modifier.background(NeeGongNaeGongTheme.colorScheme.background),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        reportReasons.forEach { reason ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = reason,
                                        style = NeeGongNaeGongTheme.typography.labelMedium,
                                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                                    )
                                },
                                onClick = {
                                    selectedReason = reason
                                    expanded = false
                                },
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (selectedReason.isNotEmpty()) {
                        onReport(
                            UserReportData(
                                user = user,
                                reportReason = selectedReason,
                            ),
                        )
                    }
                },
                enabled = selectedReason.isNotEmpty(),
            ) {
                Text(
                    text = "확인",
                    color =
                        if (selectedReason.isNotEmpty()) {
                            Color.Red
                        } else {
                            NeeGongNaeGongTheme.colorScheme.peach
                        },
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "취소", color = NeeGongNaeGongTheme.colorScheme.primaryText)
            }
        },
        containerColor = NeeGongNaeGongTheme.colorScheme.background,
    )
}

@NeeGongNaeGongPreviews
@Composable
fun ReportDialogPreview() {
    NeeGongNaeGongTheme {
        ReportDialog(
            user = UserUiModel.toDefault(),
            onReport = {},
            onDismiss = {},
        )
    }
}
