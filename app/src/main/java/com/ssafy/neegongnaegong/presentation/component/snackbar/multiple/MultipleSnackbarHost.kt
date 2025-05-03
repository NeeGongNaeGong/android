package com.ssafy.neegongnaegong.presentation.component.snackbar.multiple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.util.pixelsToDp

@Composable
fun MultipleSnackbarHost(
    modifier: Modifier = Modifier,
    multipleSnackbarState: MultipleSnackbarState,
    maxShowCount: Int = 4,
    snackbar: @Composable (MultipleSnackbarState.SnackbarEntry) -> Unit
) {
    var itemHeightPixels by remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels)

    LazyColumn(
        modifier = Modifier
            .heightIn(max = (itemHeightDp + 8.dp) * maxShowCount + 8.dp)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        reverseLayout = true
    ) {
        items(
            items = multipleSnackbarState.snackbars,
            key = { it.id }
        ) { entry ->
            Box(
                modifier = Modifier
                    .animateItem(fadeInSpec = null, fadeOutSpec = null)
                    .onSizeChanged { size -> itemHeightPixels = size.height }
            ) {
                snackbar(entry)
            }
        }
    }
}
