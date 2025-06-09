package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.VoteOptions
import com.ssafy.neegongnaegong.presentation.group.list.vote.component.OptionButton
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VoteDetailRoute(
    backStackEntry: NavBackStackEntry,
    viewModel: VoteDetailViewModel = hiltViewModel(backStackEntry),
    navigateToVotedPersonList: (title: String, votedMemberInfo: List<StudyGroupVoteStatusInfo.VotedMemberInfo>) -> Unit,
    popBackStack: () -> Boolean,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                VoteDetailContract.Effect.NavigateToBackStack -> {
                    popBackStack()
                }

                is VoteDetailContract.Effect.NavigateToVotedPersonList ->
                    navigateToVotedPersonList(
                        it.title,
                        it.votedMemberInfo,
                    )
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
        VoteDetailScreen(
            userName = state.userName,
            userProfileImg = state.userProfileImg,
            progressTime = state.progressTime,
            voteTitle = state.voteTitle,
            selected = state.selected,
            voteOptions = state.voteOptions,
            voteItems = state.voteItems,
            voteValues = state.voteValues,
            editMode = state.editMode,
            onClickOption = { optionId, optionName ->
                viewModel.setEvent(
                    VoteDetailContract.Event.SelectOption(
                        optionId,
                        optionName,
                    ),
                )
            },
            onClickShowPersonList = { title: String, votedMemberInfos: List<StudyGroupVoteStatusInfo.VotedMemberInfo> ->
                viewModel.setEvent(
                    VoteDetailContract.Event.OnClickVotedPersonList(
                        title,
                        votedMemberInfos,
                    ),
                )
            },
            onCastVote = {
                viewModel.setEvent(
                    if (state.editMode) {
                        VoteDetailContract.Event.CastVote
                    } else {
                        VoteDetailContract.Event.ToggleEditMode
                    },
                )
            },
        )
    }
}

@Composable
fun VoteDetailScreen(
    userName: String,
    userProfileImg: String,
    progressTime: String,
    voteTitle: String,
    selected: PersistentList<StudyGroupVoteDetailInfo.VoteValue>,
    voteOptions: PersistentList<VoteOptions>,
    voteItems: PersistentList<StudyGroupVoteStatusInfo>,
    voteValues: PersistentList<StudyGroupVoteDetailInfo.VoteValue>,
    editMode: Boolean,
    onClickOption: (Long, String) -> Unit,
    onClickShowPersonList: (String, List<StudyGroupVoteStatusInfo.VotedMemberInfo>) -> Unit,
    onCastVote: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = NeeGongNaeGongTheme.paddingScheme.sp2),
        verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp4),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2)) {
            GlideImage(
                imageModel = { userProfileImg },
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
                    text = userName,
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                Text(
                    text = progressTime,
                    style = NeeGongNaeGongTheme.typography.labelMedium,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                )
            }
        }
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(2.dp, NeeGongNaeGongTheme.colorScheme.gray2),
                        shape = RoundedCornerShape(5.dp),
                    ),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(NeeGongNaeGongTheme.paddingScheme.sp3),
                verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp4),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = voteTitle,
                        overflow = TextOverflow.Ellipsis,
                        style = NeeGongNaeGongTheme.typography.titleLarge,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                    if (voteOptions.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            text =
                                voteOptions.mapIndexed { index, s ->
                                    if (index != 0) {
                                        "ㆍ${s.description}"
                                    } else {
                                        s.description
                                    }
                                }.joinToString(""),
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp3),
                ) {
                    items(
                        items = voteItems,
                        key = { voteItem -> voteItem.voteItemId },
                    ) { voteItem ->
                        OptionButton(
                            modifier = Modifier.fillMaxWidth(),
                            progress =
                                voteItem.votedMembers.size.toFloat() /
                                    voteItems.maxBy { it.votedMembers.size }
                                        .votedMembers.size.let { if (it == 0) 1 else it },
                            isAnonymous =
                                voteOptions.find { it == VoteOptions.IS_SECRET }
                                    ?.let { true } ?: false,
                            alreadyVoted = !voteValues.isEmpty(),
                            votedUsers = voteItem.votedMembers,
                            isSelected =
                                selected.find { voteItem.voteItemId == it.voteItemId }
                                    ?.let { true } ?: false,
                            optionTitle = voteItem.voteItemName,
                            editMode = editMode,
                            onClick = { onClickOption(voteItem.voteItemId, voteItem.voteItemName) },
                            onClickPersonList = {
                                onClickShowPersonList(
                                    voteItem.voteItemName,
                                    voteItem.votedMembers,
                                )
                            },
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !(selected.isEmpty() && voteValues.isEmpty()),
                    onClick = onCastVote,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = NeeGongNaeGongTheme.colorScheme.yellow,
                            contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                            disabledContainerColor = NeeGongNaeGongTheme.colorScheme.gray3,
                            disabledContentColor = NeeGongNaeGongTheme.colorScheme.gray4,
                        ),
                ) {
                    Text(
                        text =
                            if (voteValues.isEmpty()) {
                                "투표하기"
                            } else {
                                "다시 투표하기"
                            },
                        style = NeeGongNaeGongTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewVoteDetailScreen() {
    NeeGongNaeGongTheme {
        VoteDetailScreen(
            userName = "",
            userProfileImg = "",
            progressTime = "",
            voteTitle = "가능한 날",
            voteOptions = persistentListOf(VoteOptions.IS_SECRET, VoteOptions.IS_MULTIPLE),
            voteItems =
                persistentListOf(
                    StudyGroupVoteStatusInfo(
                        voteItemId = 0,
                        voteItemName = "String",
                        voteItemValue = 0,
                        votedMembers =
                            persistentListOf(
                                StudyGroupVoteStatusInfo.VotedMemberInfo(
                                    0,
                                    ",",
                                    "",
                                ),
                            ),
                    ),
                ),
            voteValues = persistentListOf(),
            selected = persistentListOf(),
            editMode = false,
            onCastVote = {},
            onClickShowPersonList = { _, _ -> },
            onClickOption = { _, _ -> },
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewVoteDetailScreenEditMode() {
    NeeGongNaeGongTheme {
        VoteDetailScreen(
            userName = "",
            userProfileImg = "",
            progressTime = "",
            voteTitle = "가능한 날",
            voteOptions = persistentListOf(VoteOptions.IS_SECRET, VoteOptions.IS_MULTIPLE),
            voteItems =
                persistentListOf(
                    StudyGroupVoteStatusInfo(
                        voteItemId = 0,
                        voteItemName = "String",
                        voteItemValue = 0,
                        votedMembers =
                            persistentListOf(
                                StudyGroupVoteStatusInfo.VotedMemberInfo(
                                    0,
                                    ",",
                                    "",
                                ),
                            ),
                    ),
                ),
            voteValues = persistentListOf(),
            selected = persistentListOf(),
            editMode = true,
            onCastVote = {},
            onClickShowPersonList = { _, _ -> },
            onClickOption = { _, _ -> },
        )
    }
}
