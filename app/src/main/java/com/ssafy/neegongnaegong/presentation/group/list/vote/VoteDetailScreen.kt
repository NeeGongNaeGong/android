package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.ssafy.neegongnaegong.presentation.group.list.component.PopupMenu
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect.NavigateToBackStack
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect.NavigateToSubTab
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect.NavigateToVotedPersonList
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.CastVote
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnCancelAddOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnChangeNewOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnClickAddOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnClickPopBackStackButton
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnClickVotedPersonList
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnConfirmAddOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnDeleteVote
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnDismissPopUp
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnEditVote
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnTogglePopup
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.SelectOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.ToggleCastMode
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.VoteOptions
import com.ssafy.neegongnaegong.presentation.group.list.vote.component.OptionButton
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun VoteDetailRoute(
    backStackEntry: NavBackStackEntry,
    viewModel: VoteDetailViewModel = hiltViewModel(backStackEntry),
    navigateToVotedPersonList: (title: String, votedMemberInfo: List<StudyGroupVoteStatusInfo.VotedMemberInfo>) -> Unit,
    popBackStack: () -> Boolean,
    navigateToSubTab: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                NavigateToBackStack -> popBackStack()

                is NavigateToVotedPersonList ->
                    navigateToVotedPersonList(
                        it.title,
                        it.votedMemberInfo,
                    )

                NavigateToSubTab -> navigateToSubTab()
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
        VoteDetailScreen(
            userName = state.userName,
            userProfileImg = state.userProfileImg,
            progressTime = state.progressTime,
            voteTitle = state.voteTitle,
            state = state.state,
            showPopup = state.showPopup,
            selected = state.selected,
            voteOptions = state.voteOptions,
            voteItems = state.voteItems,
            voteValues = state.voteValues,
            castMode = state.castMode,
            addOptionMode = state.addOptionMode,
            newOptionTitle = state.newOption,
            onClickOption = { optionId, optionName ->
                viewModel.setEvent(
                    SelectOption(
                        optionId,
                        optionName,
                    ),
                )
            },
            onClickShowPersonList = { title: String, votedMemberInfos: List<StudyGroupVoteStatusInfo.VotedMemberInfo> ->
                viewModel.setEvent(
                    OnClickVotedPersonList(
                        title,
                        votedMemberInfos,
                    ),
                )
            },
            onCastVote = {
                viewModel.setEvent(
                    if (state.castMode) {
                        CastVote
                    } else {
                        ToggleCastMode
                    },
                )
            },
            onClickAddOption = { viewModel.setEvent(OnClickAddOption) },
            onChangeNewOption = { viewModel.setEvent(OnChangeNewOption(it)) },
            onCancelNewOption = { viewModel.setEvent(OnCancelAddOption) },
            onConfirmNewOption = { viewModel.setEvent(OnConfirmAddOption) },
            onClickPopup = { viewModel.setEvent(OnTogglePopup) },
            onDismissPopup = { viewModel.setEvent(OnDismissPopUp) },
            onClickDeleteVote = { viewModel.setEvent(OnDeleteVote) },
            onClickEditVote = { viewModel.setEvent(OnEditVote) },
        )
    }
}

@Composable
private fun VoteDetailScreen(
    userName: String,
    userProfileImg: String,
    progressTime: String,
    voteTitle: String,
    state: Boolean,
    showPopup: Boolean,
    selected: PersistentList<StudyGroupVoteDetailInfo.VoteValue>,
    voteOptions: PersistentList<VoteOptions>,
    voteItems: PersistentList<StudyGroupVoteStatusInfo>,
    voteValues: PersistentList<StudyGroupVoteDetailInfo.VoteValue>,
    castMode: Boolean,
    addOptionMode: Boolean,
    newOptionTitle: String,
    onClickOption: (Long, String) -> Unit,
    onClickShowPersonList: (String, List<StudyGroupVoteStatusInfo.VotedMemberInfo>) -> Unit,
    onCastVote: () -> Unit,
    onClickAddOption: () -> Unit,
    onChangeNewOption: (String) -> Unit,
    onCancelNewOption: () -> Unit,
    onConfirmNewOption: () -> Unit,
    onClickPopup: () -> Unit,
    onDismissPopup: () -> Unit,
    onClickDeleteVote: () -> Unit,
    onClickEditVote: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = NeeGongNaeGongTheme.paddingScheme.sp2),
        verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp4),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GlideImage(
                imageModel = { userProfileImg },
                loading = { CircularProgressIndicator() },
                modifier =
                    Modifier
                        .size(50.dp)
                        .clip(CircleShape),
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
                                .clip(CircleShape),
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
                horizontalAlignment = Alignment.End,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2),
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            modifier = Modifier.weight(1F),
                            text = voteTitle,
                            overflow = TextOverflow.Ellipsis,
                            style = NeeGongNaeGongTheme.typography.titleLarge,
                            color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        )
                        PopupMenu(
                            showPopup = showPopup,
                            onClickPopup = onClickPopup,
                            onDismissPopup = onDismissPopup,
                            onClickDeleteMenu = onClickDeleteVote,
                            onClickEditMenu = onClickEditVote,
                        )
                    }
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
                            state = state,
                            votedUsers = voteItem.votedMembers,
                            isSelected =
                                selected.find { voteItem.voteItemId == it.voteItemId }
                                    ?.let { true } ?: false,
                            optionTitle = voteItem.voteItemName,
                            castMode = castMode,
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

                if (!addOptionMode) {
                    if (voteOptions.contains(VoteOptions.IS_CHOSEN) && state) {
                        Row(
                            modifier =
                                Modifier
                                    .wrapContentSize()
                                    .clickable {
                                        onClickAddOption()
                                    },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                tint = NeeGongNaeGongTheme.colorScheme.gray4,
                                contentDescription = "더하기 버튼",
                            )
                            Text(
                                text = "항목 추가",
                                color = NeeGongNaeGongTheme.colorScheme.gray4,
                                style = NeeGongNaeGongTheme.typography.labelLarge,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !((selected.isEmpty() && voteValues.isEmpty()) || !state),
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
                                if (!state) {
                                    "투표가 종료되었습니다"
                                } else if (voteValues.isEmpty()) {
                                    "투표하기"
                                } else {
                                    "다시 투표하기"
                                },
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                        )
                    }
                } else {
                    Column {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    "항목 입력",
                                    style = NeeGongNaeGongTheme.typography.labelSmall,
                                )
                            },
                            value = newOptionTitle,
                            onValueChange = onChangeNewOption,
                            colors =
                                TextFieldDefaults.colors(
                                    focusedContainerColor = NeeGongNaeGongTheme.colorScheme.gray2,
                                    unfocusedContainerColor = NeeGongNaeGongTheme.colorScheme.gray2,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    unfocusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                    focusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                ),
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2)) {
                            val buttonColors =
                                ButtonDefaults.buttonColors(
                                    containerColor = NeeGongNaeGongTheme.colorScheme.gray3,
                                    contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                    disabledContainerColor = NeeGongNaeGongTheme.colorScheme.gray2,
                                    disabledContentColor = NeeGongNaeGongTheme.colorScheme.gray4,
                                )
                            Button(
                                modifier = Modifier.weight(1F),
                                shape = RoundedCornerShape(5.dp),
                                colors = buttonColors,
                                onClick = onCancelNewOption,
                            ) {
                                Text(
                                    text = "취소",
                                    style = NeeGongNaeGongTheme.typography.labelMedium,
                                )
                            }

                            Button(
                                modifier = Modifier.weight(1F),
                                enabled = newOptionTitle.isNotEmpty(),
                                shape = RoundedCornerShape(5.dp),
                                colors = buttonColors,
                                onClick = onConfirmNewOption,
                            ) {
                                Text(
                                    text = "확인",
                                    style = NeeGongNaeGongTheme.typography.labelMedium,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewVoteDetailScreen() {
    NeeGongNaeGongTheme {
        VoteDetailScreen(
            userName = "",
            userProfileImg = "",
            progressTime = "",
            voteTitle = "가능한 날",
            state = false,
            showPopup = false,
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
            castMode = false,
            newOptionTitle = "",
            onCastVote = {},
            onClickShowPersonList = { _, _ -> },
            onClickOption = { _, _ -> },
            onClickAddOption = {},
            addOptionMode = false,
            onChangeNewOption = {},
            onCancelNewOption = {},
            onConfirmNewOption = {},
            onClickPopup = {},
            onClickEditVote = {},
            onClickDeleteVote = {},
            onDismissPopup = {},
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewVoteDetailScreenCastMode() {
    NeeGongNaeGongTheme {
        VoteDetailScreen(
            userName = "",
            userProfileImg = "",
            progressTime = "",
            voteTitle = "가능한 날",
            state = true,
            showPopup = false,
            voteOptions =
                persistentListOf(
                    VoteOptions.IS_SECRET,
                    VoteOptions.IS_MULTIPLE,
                    VoteOptions.IS_CHOSEN,
                ),
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
            castMode = true,
            newOptionTitle = "",
            onCastVote = {},
            onClickShowPersonList = { _, _ -> },
            onClickOption = { _, _ -> },
            onClickAddOption = {},
            addOptionMode = true,
            onChangeNewOption = {},
            onCancelNewOption = {},
            onConfirmNewOption = {},
            onClickPopup = {},
            onClickEditVote = {},
            onClickDeleteVote = {},
            onDismissPopup = {},
        )
    }
}
