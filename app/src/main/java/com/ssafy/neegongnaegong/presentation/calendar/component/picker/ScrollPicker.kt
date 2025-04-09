package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.pixelsToDp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun <T> rememberPickerState(initialValue: T) = remember { PickerState(initialValue) }

class PickerState<T>(initialValue: T) {
    var selectedItem by mutableStateOf<T>(initialValue)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> ScrollPicker(
    modifier: Modifier = Modifier,
    items: List<T>,
    state: PickerState<T> = rememberPickerState(items.first()),
    visibleItemsCount: Int = 3,
    isInfinite: Boolean = true,
    text: (T) -> String = { it.toString() }
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = if (isInfinite) Integer.MAX_VALUE else items.size
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = if (isInfinite) {
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + items.indexOf(state.selectedItem)
    } else {
        items.indexOf(state.selectedItem)
    }

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    var itemHeightPixels by remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(state.selectedItem) {
        listState.scrollToItem(listStartIndex)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index + visibleItemsMiddle - if(isInfinite) 0 else visibleItemsCount / 2 }
            .filter { if(isInfinite) true else it in items.indices }
            .map { getItem(it) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(itemHeightDp * visibleItemsCount)
            .fadingEdge(fadingEdgeGradient)
    ) {
        if (!isInfinite) items(visibleItemsCount / 2) {
            Text(
                modifier = Modifier
                    .onSizeChanged { size -> itemHeightPixels = size.height }
                    .padding(vertical = 4.dp),
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        items(listScrollCount) { index ->
            Text(
                modifier = Modifier
                    .onSizeChanged { size -> itemHeightPixels = size.height }
                    .padding(vertical = 4.dp),
                text = text(getItem(index)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        if (!isInfinite) items(visibleItemsCount / 2) {
            Text(
                modifier = Modifier
                    .onSizeChanged { size -> itemHeightPixels = size.height }
                    .padding(vertical = 4.dp),
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }


@Preview
@Composable
private fun NumberPickerPreview() {
    NeeGongNaeGongTheme {
        Surface {
            ScrollPicker(
                modifier = Modifier.fillMaxWidth(),
                items = listOf(1, 2, 3, 4, 5),
                isInfinite = false
            )
        }
    }
}