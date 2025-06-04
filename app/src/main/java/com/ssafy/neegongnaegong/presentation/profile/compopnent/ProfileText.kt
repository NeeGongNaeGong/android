package com.ssafy.neegongnaegong.presentation.profile.compopnent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ProfileNickname(
    modifier: Modifier = Modifier,
    isEditing: Boolean,
    text: String,
    onEditText: (String) -> Unit
) {
    if (isEditing) {
        ProfileEditText(modifier = modifier)
    } else {
        ProfileText(modifier = modifier, text = text)
    }
}

@Composable
fun ProfileEditText(modifier: Modifier = Modifier) {

}

@Composable
fun ProfileText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = NeeGongNaeGongTheme.typography.bodySmall
    )
}
