package com.ssafy.neegongnaegong.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer


@Composable
fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

@Composable
fun getTextWidthDp(text: String, textStyle: TextStyle) = with(LocalDensity.current) {
    val textMeasurer = rememberTextMeasurer()
    textMeasurer.measure(text, style = textStyle).size.width.toDp()
}

@Composable
fun getTextHeightDp(text: String, textStyle: TextStyle) = with(LocalDensity.current) {
    val textMeasurer = rememberTextMeasurer()
    textMeasurer.measure(text, style = textStyle).size.height.toDp()
}

