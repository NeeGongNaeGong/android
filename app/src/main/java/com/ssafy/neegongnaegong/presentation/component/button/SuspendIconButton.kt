package com.ssafy.neegongnaegong.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            )
            .then(modifier),
        onClick = onClick,
        enabled = !isLoading
    ) {
        when (isLoading) {
            true -> CircularProgressIndicator(
                modifier = Modifier.padding(4.dp),
                color = MaterialTheme.colorScheme.primary
            )

            false -> Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}