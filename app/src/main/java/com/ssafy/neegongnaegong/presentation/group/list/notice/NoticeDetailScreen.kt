package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event.OnClickPopBackStackButton
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event.OnDeleteNotice
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event.OnDismissPopUp
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event.OnEditNotice
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event.OnTogglePopup
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun NoticeDetailRoute(
    backStackEntry: NavBackStackEntry,
    viewModel: NoticeDetailViewModel = hiltViewModel(backStackEntry),
    popBackStack: () -> Boolean,
    navigateToSubTab: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                NavigateToBackStack -> popBackStack()

                NoticeDetailContract.Effect.NavigateToSubTab -> navigateToSubTab()
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
            onNavigationClick = { viewModel.setEvent(OnClickPopBackStackButton) },
        )
        NoticeDetailScreen(
            writer = state.writer,
            writerProfileImage = state.writerProfileImage,
            createdAt = state.createdAt,
            showPopup = state.showPopup,
            onClickPopup = { viewModel.setEvent(OnTogglePopup) },
            onDismissPopup = { viewModel.setEvent(OnDismissPopUp) },
            onClickDeleteNotice = { viewModel.setEvent(OnDeleteNotice) },
            onClickEditNotice = { viewModel.setEvent(OnEditNotice) },
            content = state.content,
        )
    }
}

@Composable
private fun NoticeDetailScreen(
    writer: String,
    writerProfileImage: String,
    createdAt: String,
    showPopup: Boolean,
    onClickPopup: () -> Unit,
    onDismissPopup: () -> Unit,
    onClickDeleteNotice: () -> Unit,
    onClickEditNotice: () -> Unit,
    content: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp3),
        modifier = Modifier.padding(NeeGongNaeGongTheme.paddingScheme.sp3),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                modifier = Modifier.weight(1F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2),
            ) {
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
                Column(Modifier.weight(1F), verticalArrangement = Arrangement.Center) {
                    Text(
                        text = writer,
                        overflow = TextOverflow.Ellipsis,
                        style = NeeGongNaeGongTheme.typography.titleSmall,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                    Text(
                        text = createdAt,
                        overflow = TextOverflow.Ellipsis,
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    )
                }
            }
            Box {
                IconButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = onClickPopup,
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "더보기 아이콘",
                    )
                }
                DropdownMenu(
                    expanded = showPopup,
                    onDismissRequest = onDismissPopup,
                    containerColor = NeeGongNaeGongTheme.colorScheme.background,
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally),
                                text = "삭제하기",
                                style = NeeGongNaeGongTheme.typography.labelMedium,
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        contentPadding = PaddingValues(0.dp),
                        onClick = onClickDeleteNotice,
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally),
                                text = "수정하기",
                                style = NeeGongNaeGongTheme.typography.labelMedium,
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        contentPadding = PaddingValues(0.dp),
                        onClick = onClickEditNotice,
                    )
                }
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
private fun PreviewNoticeDetailScreen() {
    NeeGongNaeGongTheme {
        NoticeDetailScreen(
            "테스트",
            "ㅈㄷㄹㅈㄷㄹ",
            "ㅈㄷㄹㅈㄷㄹ",
            true,
            {},
            {},
            {},
            {},
            "ㅈㄷㄹㅈㄷㄹ",
        )
    }
}
