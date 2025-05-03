package com.ssafy.neegongnaegong.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun SuspendIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = Modifier
            .background(
                color = NeeGongNaeGongTheme.colorScheme.blue,
                shape = CircleShape
            )
            .then(modifier),
        onClick = onClick,
        enabled = !isLoading
    ) {
        when (isLoading) {
            true -> CircularProgressIndicator(
                modifier = Modifier.padding(4.dp),
                color = NeeGongNaeGongTheme.colorScheme.primaryText
            )

            false -> Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = NeeGongNaeGongTheme.colorScheme.primaryText
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun SuspendIconButtonPreview() {
    NeeGongNaeGongTheme {
        SuspendIconButton(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Schedule",
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun SuspendIconButtonPreview_Loading() {
    NeeGongNaeGongTheme {
        SuspendIconButton(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Schedule",
            isLoading = true
        )
    }
}
