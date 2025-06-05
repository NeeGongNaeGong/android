package com.ssafy.neegongnaegong.presentation.profile.compopnent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.component.edittext.PlainEditText
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

private const val MaxNickNameLength: Int = 15
private val IconSize: Dp = 18.dp

@Composable
fun ProfileNickname(
    modifier: Modifier = Modifier,
    isEditing: Boolean,
    text: String,
    onClickEdit: () -> Unit,
    onClickEditCancel: () -> Unit,
    onClickEditDone: (String) -> Unit
) {
    if (isEditing) {
        ProfileEditText(
            modifier = modifier,
            text = text,
            onClickEditCancel = onClickEditCancel,
            onClickEditDone = onClickEditDone,
        )
    } else {
        ProfileText(
            modifier = modifier,
            text = text,
            onClickEdit = onClickEdit
        )
    }
}

@Composable
private fun ProfileEditText(
    modifier: Modifier = Modifier,
    text: String,
    onClickEditCancel: () -> Unit,
    onClickEditDone: (String) -> Unit
) {
    val (value, onValueChange) = rememberSaveable { mutableStateOf(text) }
    val focusManager = LocalFocusManager.current

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        PlainEditText(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { newValue: String ->
                if (newValue.length <= MaxNickNameLength) onValueChange(newValue)
            },
            singleLine = true,
        )

        TrailingIcon(
            imageVector = Icons.Default.Check,
            iconSize = IconSize + 2.dp,
            onClick = {
                focusManager.clearFocus(force = true)
                onClickEditDone(value)
            }
        )

        TrailingIcon(
            imageVector = Icons.Default.Close,
            iconSize = IconSize + 2.dp,
            onClick = onClickEditCancel
        )
    }
}

@Composable
private fun ProfileText(
    modifier: Modifier = Modifier,
    text: String,
    onClickEdit: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = NeeGongNaeGongTheme.typography.bodySmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText
        )

        TrailingIcon(imageVector = Icons.Default.Edit, onClick = onClickEdit)
    }
}


@Composable
private fun TrailingIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconSize: Dp = IconSize,
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier
            .padding(start = 12.dp)
            .size(size = iconSize)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onClick
            ),
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = NeeGongNaeGongTheme.colorScheme.primaryText
    )
}
