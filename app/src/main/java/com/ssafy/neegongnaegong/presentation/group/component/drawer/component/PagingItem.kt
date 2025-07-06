package com.ssafy.neegongnaegong.presentation.group.component.drawer.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingItem() {
    Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
}

@Composable
fun ErrorItem(
    message: String,
    onRetry: () -> Unit,
) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("Error: $message", Modifier.clickable { onRetry() })
    }
}

@Composable
fun NoDataItem() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("가입하신 그룹은 이곳이 전부입니다!")
    }
}
