package com.ssafy.neegongnaegong.presentation.group.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

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
        Text(
            text = "Error: $message",
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            style = NeeGongNaeGongTheme.typography.bodyMedium,
            modifier = Modifier.clickable { onRetry() },
        )
    }
}

@Composable
fun NoDataItem(from: From) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text(
            text = "아직 등록된 ${from.route}가 없습니다!",
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            style = NeeGongNaeGongTheme.typography.bodyMedium,
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewLoadingItem() {
    NeeGongNaeGongTheme {
        LoadingItem()
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewErrorItem() {
    NeeGongNaeGongTheme {
        ErrorItem("에러가 났습니다", {})
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNoDataItem() {
    NeeGongNaeGongTheme {
        NoDataItem(From.Notice)
    }
}

enum class From(val route: String) {
    Notice("공지"),
    Vote("투표"),
}
