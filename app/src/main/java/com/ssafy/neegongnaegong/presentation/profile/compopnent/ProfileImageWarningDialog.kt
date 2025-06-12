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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

private val Orange = Color(0xFFF97316)
private val Red = Color(0xFFEF4444)
private val HeadColors = listOf(Orange, Red)
private val Blue = Color(0xFF2563EB)
private val LightGray = Color(0xFFD1D5DB)

@Composable
fun ProfileImageWarningDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    var isConfirmed: Boolean by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false),
    ) {
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = NeeGongNaeGongTheme.colorScheme.background,
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                WarningDialogHead()

                WarningDialogContent(
                    isConfirmed = isConfirmed,
                    setConfirmed = { isConfirmed = it },
                )

                WarningDialogFoot(
                    isConfirmed = isConfirmed,
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }
    }
}

@Composable
private fun WarningDialogHead(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(brush = Brush.horizontalGradient(colors = HeadColors))
                .padding(20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .background(
                            Color.White.copy(alpha = 0.2f),
                            CircleShape,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Warning,
                    tint = NeeGongNaeGongTheme.colorScheme.background,
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.profile_image_change_guide),
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.background,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.check_community_guideline),
                    style = NeeGongNaeGongTheme.typography.labelSmall,
                    color = NeeGongNaeGongTheme.colorScheme.background,
                )
            }
        }
    }
}

@Composable
private fun WarningDialogFoot(
    modifier: Modifier = Modifier,
    isConfirmed: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp, top = 6.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onDismiss,
            colors =
                ButtonDefaults.textButtonColors(
                    contentColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                ),
        ) {
            Text(
                text = stringResource(id = R.string.common_cancel),
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                style =
                    NeeGongNaeGongTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                    ),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = onConfirm,
            enabled = isConfirmed,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = if (isConfirmed) Blue else NeeGongNaeGongTheme.colorScheme.secondaryText,
                    contentColor = if (isConfirmed) Color.White else NeeGongNaeGongTheme.colorScheme.secondaryText,
                ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.common_continue),
                style =
                    NeeGongNaeGongTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                color =
                    if (isConfirmed) {
                        NeeGongNaeGongTheme.colorScheme.background
                    } else {
                        NeeGongNaeGongTheme.colorScheme.secondaryText
                    },
            )
        }
    }
}

@Composable
private fun WarningDialogContent(
    modifier: Modifier = Modifier,
    isConfirmed: Boolean,
    setConfirmed: (Boolean) -> Unit,
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 24.dp)) {
            Text(
                text = stringResource(id = R.string.profile_image_change_notice),
                style = NeeGongNaeGongTheme.typography.bodySmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )

            Spacer(modifier = Modifier.height(8.dp))

            listOf(
                stringResource(id = R.string.guideline_sexual_content),
                stringResource(id = R.string.guideline_violent_content),
                stringResource(id = R.string.guideline_offensive_content),
            ).forEach { guideline: String ->
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = "• $guideline",
                    style =
                        NeeGongNaeGongTheme.typography.labelSmall.copy(
                            fontSize = 13.sp,
                        ),
                    color = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.6f),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.community_guideline_thanks),
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                style =
                    NeeGongNaeGongTheme.typography.labelSmall.copy(
                        fontSize = 13.sp,
                    ),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 12.dp),
        ) {
            Checkbox(
                checked = isConfirmed,
                onCheckedChange = setConfirmed,
                colors =
                    CheckboxDefaults.colors(
                        checkedColor = Blue,
                        uncheckedColor = LightGray,
                    ),
            )

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.confirm_guideline_check),
                color = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.7f),
                style = NeeGongNaeGongTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun ProfileImageWarningDialogPreview() {
    ProfileImageWarningDialog(
        onDismiss = {},
        onConfirm = {},
    )
}
