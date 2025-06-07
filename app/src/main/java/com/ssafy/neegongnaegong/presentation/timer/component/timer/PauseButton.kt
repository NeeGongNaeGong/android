package com.ssafy.neegongnaegong.presentation.timer.component.timer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun PauseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedButton(
        modifier =
            modifier
                .size(88.dp),
        onClick = onClick,
        shape = CircleShape,
        border = BorderStroke(4.dp, NeeGongNaeGongTheme.colorScheme.primaryText),
        contentPadding = PaddingValues(0.dp),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
            ),
        interactionSource = interactionSource,
    ) {
        Icon(
            imageVector = Icons.Default.Pause,
            contentDescription = stringResource(R.string.txt_timer_pause),
            modifier = Modifier.size(40.dp),
            tint = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PauseButtonPreview() {
    NeeGongNaeGongTheme {
        PauseButton(onClick = {})
    }
}
