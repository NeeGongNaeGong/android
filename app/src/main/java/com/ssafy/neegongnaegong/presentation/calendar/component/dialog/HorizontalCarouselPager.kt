package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun HorizontalCarouselPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    beyondViewportPageCount: Int = 1,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp),
    pageSpacing: Dp = 8.dp,
    content: @Composable (Int) -> Unit,
) {
    HorizontalPager(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .then(modifier),
        state = state,
        beyondViewportPageCount = beyondViewportPageCount,
        contentPadding = contentPadding,
        pageSpacing = pageSpacing,
    ) { page ->
        key(page) {
            Box(
                modifier =
                    Modifier.graphicsLayer {
                        val pageOffSet = ((state.currentPage - page) + state.currentPageOffsetFraction).absoluteValue
                        alpha =
                            lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffSet.coerceIn(0f, 1f),
                            )
                        scaleY =
                            lerp(
                                start = 0.9f,
                                stop = 1f,
                                fraction = 1f - pageOffSet.coerceIn(0f, 1f),
                            )
                    },
            ) {
                content(page)
            }
        }
    }
}
