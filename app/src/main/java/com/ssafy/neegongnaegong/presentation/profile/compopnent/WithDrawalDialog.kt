package com.ssafy.neegongnaegong.presentation.profile.compopnent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

private val Red = Color(0xFFEF4444)

@Composable
fun WithdrawalDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var isConfirmed by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = NeeGongNaeGongTheme.colorScheme.background,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 경고 아이콘
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = Color(0xFFFEE2E2),
                            shape = RoundedCornerShape(32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "경고",
                        tint = Red,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 제목
                Text(
                    text = "정말로 탈퇴하시겠습니까?",
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "회원탈퇴를 진행하시면 다음과 같은 정보들이 영구적으로 삭제됩니다",
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText
                )

                // 삭제 항목 리스트
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = NeeGongNaeGongTheme.colorScheme.background
                    ),
                ) {
                    Column(modifier = Modifier.padding(vertical = 16.dp)) {
                        DeletionItem("회원 정보 및 프로필")
                        DeletionItem("활동 내역 및 게시글")
                    }
                }

                Text(
                    text = "탈퇴 후에는 복구가 불가능하며, 동일한 이메일로 재가입 시에도 기존 정보는 복원되지 않습니다.",
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Red
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isConfirmed,
                        onCheckedChange = { isConfirmed = it },
                        colors = CheckboxDefaults.colors(checkedColor = Red)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "위 내용을 모두 확인했으며, 회원탈퇴에 동의합니다.",
                        style = NeeGongNaeGongTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE5E7EB)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "취소",
                            color = Color(0xFF374151),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Button(
                        onClick = onConfirm,
                        enabled = isConfirmed,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isConfirmed) Red else Color(0xFFF3F4F6),
                            disabledContainerColor = Color(0xFFF3F4F6)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "회원탈퇴",
                            color = if (isConfirmed) Color.White else Color(0xFF9CA3AF),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "탈퇴를 원하지 않으시면 언제든지 취소하실 수 있습니다.",
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DeletionItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "•",
            color = Red,
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 8.dp, top = 2.dp)
        )

        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF374151),
            lineHeight = 20.sp
        )
    }
}
