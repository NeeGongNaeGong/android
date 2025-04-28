package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.presentation.base.SnackbarViewModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

@Composable
fun NeeGongNaeGongSnackbarHost() {
    val snackbarViewModel: SnackbarViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarViewModel.message.collect {
            val (message, type) = it
            val visuals = NeeGongNaeGongSnackbarVisuals(message, type)
            snackbarHostState.showSnackbar(visuals)
        }
    }

    SnackbarHost(hostState = snackbarHostState) {
        val visuals = it.visuals as? NeeGongNaeGongSnackbarVisuals ?: return@SnackbarHost
        val (message, type) = visuals
        val backgroundColor = when(type) {
            SnackbarManager.Type.Success -> NeeGongNaeGongTheme.colorScheme.gray3
            SnackbarManager.Type.Warning -> NeeGongNaeGongTheme.colorScheme.lightGreen
            SnackbarManager.Type.Error -> NeeGongNaeGongTheme.colorScheme.peach
            SnackbarManager.Type.None -> NeeGongNaeGongTheme.colorScheme.mint
        }

        Card(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = message,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        }
    }
}
