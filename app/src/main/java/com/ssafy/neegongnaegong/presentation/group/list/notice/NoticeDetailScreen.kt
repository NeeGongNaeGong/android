package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Effect.NavigateToBackStack
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoticeDetailRoute(
    backStackEntry: NavBackStackEntry,
    viewModel: NoticeDetailViewModel = hiltViewModel(backStackEntry),
    popBackStack: () -> Boolean,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                NavigateToBackStack -> {
                    popBackStack()
                }
            }
        }
    }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "상세보기",
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            onNavigationClick = { popBackStack() },
        )
        NoticeDetailScreen(
            writer = state.writer,
            writerProfileImage = state.writerProfileImage,
            createdAt = state.createdAt,
            content = state.content,
        )
    }
}

@Composable
fun NoticeDetailScreen(
    writer: String,
    writerProfileImage: String,
    createdAt: String,
    content: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp3),
        modifier = Modifier.padding(NeeGongNaeGongTheme.paddingScheme.sp3),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2)) {
            GlideImage(
                imageModel = { writerProfileImage },
                loading = { CircularProgressIndicator() },
                modifier =
                    Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(10.dp)),
                imageOptions =
                    ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                    ),
                requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) },
                failure = {
                    // 이미지 로드 실패 시 플레이스홀더
                    Image(
                        painter = painterResource(id = R.drawable.img_default_profile),
                        contentDescription = "Profile Image",
                        modifier =
                            Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,
                    )
                },
            )
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = writer,
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                Text(
                    text = createdAt,
                    style = NeeGongNaeGongTheme.typography.labelMedium,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                )
            }
        }
        Text(
            modifier = Modifier.fillMaxSize(),
            text = content,
            style = NeeGongNaeGongTheme.typography.bodyMedium,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNoticeDetailScreen() {
    NeeGongNaeGongTheme {
        NoticeDetailScreen(
            "테스트",
            "ㅈㄷㄹㅈㄷㄹ",
            "ㅈㄷㄹㅈㄷㄹ",
            "ㅈㄷㄹㅈㄷㄹ",
        )
    }
}
