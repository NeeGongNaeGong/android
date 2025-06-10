package com.ssafy.neegongnaegong.presentation.group.edit

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.component.StudiesCategoryDropdown
import com.ssafy.neegongnaegong.presentation.group.component.StudiesImagePicker
import com.ssafy.neegongnaegong.presentation.group.component.StudiesMaxMemberDropdown
import com.ssafy.neegongnaegong.presentation.group.component.StudiesTimeDropdown
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.FileUtils.getFileExtension
import com.ssafy.neegongnaegong.presentation.util.FileUtils.uriToRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.RequestBody

@Composable
fun StudiesEditRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesEditViewModel = hiltViewModel(),
    studyGroupId: Long,
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesEditContract.Event.OnLoad(studyGroupId))
    }

    StudiesEditContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onNameChanged = { viewModel.setEvent(StudiesEditContract.Event.OnNameChanged(it)) },
        onIsPublicChanged = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnIsPublicChanged(it),
            )
        },
        onTargetStudyTimeChanged = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnTargetStudyTimeChanged(it),
            )
        },
        onMaxMembersChanged = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnMaxMembersChanged(it),
            )
        },
        onCategorySelected = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnSelectedCategory(it),
            )
        },
        onTagSelected = { viewModel.setEvent(StudiesEditContract.Event.OnTagSelected(it)) },
        onTagUnSelected = { viewModel.setEvent(StudiesEditContract.Event.OnTagUnSelected(it)) },
        onDescriptionChanged = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnDescriptionChanged(it),
            )
        },
        onClearProfileImg = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnClearProfileImg,
            )
        },
        onSelectedImage = { uri, ext ->
            viewModel.setEvent(
                StudiesEditContract.Event.OnSelectedImage(extension = ext),
            )
        },
        onSelectedImageRequest = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnSelectedImageRequest(it),
            )
        },
        onCreateStudies = {
            viewModel.setEvent(
                StudiesEditContract.Event.OnCreateStudiesClicked,
            )
        },
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesEditContent(
    modifier: Modifier = Modifier,
    effect: Flow<StudiesEditContract.Effect>,
    uiState: StudiesEditContract.State,
    onNameChanged: (String) -> Unit,
    onIsPublicChanged: (Boolean) -> Unit,
    onTargetStudyTimeChanged: (Int) -> Unit,
    onMaxMembersChanged: (Int) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagUnSelected: (Tag) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onClearProfileImg: () -> Unit,
    onSelectedImage: (Uri, String?) -> Unit,
    onSelectedImageRequest: (RequestBody?) -> Unit,
    onCreateStudies: () -> Unit,
    popBackStack: () -> Unit,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                StudiesEditContract.Effect.NavigateToBack -> backDispatcher?.onBackPressed()
            }
        }
    }
    StudiesEditScreen(
        modifier = modifier,
        name = uiState.studyInfo.name,
        isPublic = uiState.studyInfo.isPublic,
        targetStudyTime = uiState.studyInfo.targetStudyTime,
        currentMembers = uiState.currentMembers,
        maxMembers = uiState.studyInfo.maxMembers,
        selectedCategory = uiState.selectedCategory,
        categories = uiState.categories,
        selectedTags = uiState.selectedTags,
        tags = uiState.tags,
        description = uiState.studyInfo.description,
        profileImg = uiState.studyInfo.profileImg,
        validateCreateStudies = uiState.validateCreateStudies,
        onNameChanged = onNameChanged,
        onIsPublicChanged = onIsPublicChanged,
        onTargetStudyTimeChanged = onTargetStudyTimeChanged,
        onMaxMembersChanged = onMaxMembersChanged,
        onCategorySelected = onCategorySelected,
        onTagSelected = onTagSelected,
        onTagUnSelected = onTagUnSelected,
        onDescriptionChanged = onDescriptionChanged,
        onClearProfileImg = onClearProfileImg,
        onSelectedImage = onSelectedImage,
        onSelectedImageRequest = onSelectedImageRequest,
        onCreateStudies = onCreateStudies,
        popBackStack = popBackStack,
    )

    if (uiState.isOnUpdate) LoadingDialog()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StudiesEditScreen(
    modifier: Modifier = Modifier,
    name: String,
    isPublic: Boolean,
    targetStudyTime: Int,
    currentMembers: Int,
    maxMembers: Int,
    selectedCategory: Category?,
    categories: List<Category>,
    selectedTags: List<Tag>,
    tags: List<Tag>,
    description: String,
    profileImg: String?,
    validateCreateStudies: Boolean,
    onNameChanged: (String) -> Unit,
    onIsPublicChanged: (Boolean) -> Unit,
    onTargetStudyTimeChanged: (Int) -> Unit,
    onMaxMembersChanged: (Int) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagUnSelected: (Tag) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onClearProfileImg: () -> Unit,
    onSelectedImage: (Uri, String?) -> Unit,
    onSelectedImageRequest: (RequestBody?) -> Unit,
    onCreateStudies: () -> Unit,
    popBackStack: () -> Unit = {},
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val photoFromAlbumLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let {
                    selectedImageUri = it
                    onSelectedImage(it, context.getFileExtension(it))
                    onSelectedImageRequest(context.uriToRequestBody(it))
                }
            },
        )

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = stringResource(R.string.studies_edit_title),
                    style = NeeGongNaeGongTheme.typography.titleMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            onNavigationClick = popBackStack,
        )
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
        ) {
            // 스터디명 섹션
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = stringResource(R.string.studies_edit_study_name),
                style = NeeGongNaeGongTheme.typography.titleSmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
            val nameMaxLength = 20
            OutlinedTextField(
                value = name,
                onValueChange = {
                    if (it.length <= nameMaxLength) {
                        onNameChanged(it)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "스터디 이름을 입력하세요",
                        style = NeeGongNaeGongTheme.typography.bodySmall,
                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    )
                },
                singleLine = true,
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = "${name.length} / $nameMaxLength",
                            style = NeeGongNaeGongTheme.typography.bodySmall,
                            color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        )
                    }
                },
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        unfocusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        cursorColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                        focusedBorderColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                        selectionColors =
                            TextSelectionColors(
                                handleColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                                backgroundColor =
                                    NeeGongNaeGongTheme.colorScheme.mintBlue.copy(
                                        alpha = 0.3f,
                                    ),
                            ),
                    ),
            )

            // 공개/비공개 설정
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.studies_edit_public_private),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = if (isPublic) R.drawable.ic_studies_public else R.drawable.ic_studies_private),
                        tint = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        contentDescription = null,
                    )
                    Switch(
                        checked = isPublic,
                        onCheckedChange = { onIsPublicChanged(it) },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = NeeGongNaeGongTheme.colorScheme.background,
                                checkedTrackColor = NeeGongNaeGongTheme.colorScheme.blue,
                                uncheckedThumbColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                                uncheckedTrackColor = NeeGongNaeGongTheme.colorScheme.gray2,
                            ),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.studies_edit_study_time),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                StudiesTimeDropdown(
                    modifier = modifier,
                    targetStudyTime = targetStudyTime,
                    onTargetStudyTimeChanged = onTargetStudyTimeChanged,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.studies_edit_max_members),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                StudiesMaxMemberDropdown(
                    modifier = Modifier,
                    currentMembers = currentMembers,
                    maxMembers = maxMembers,
                    onMaxMembersChanged = onMaxMembersChanged,
                )
            }

            HorizontalDivider(
                modifier =
                    Modifier
                        .padding(vertical = 10.dp),
                color = NeeGongNaeGongTheme.colorScheme.gray3,
            )
            // 카테고리 선택
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.studies_edit_select_category),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                StudiesCategoryDropdown(
                    modifier = Modifier,
                    selectedCategory = selectedCategory,
                    categories = categories,
                    onCategorySelected = onCategorySelected,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            // 태그 선택
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = stringResource(R.string.studies_edit_select_tags),
                style = NeeGongNaeGongTheme.typography.titleSmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )

            if (selectedCategory == null && tags.isEmpty()) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                    text = "카레고리를 선택해주세요",
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    textAlign = TextAlign.Center,
                )
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                tags.forEach { tag ->
                    val isSelected = tag in selectedTags
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) {
                                onTagUnSelected(tag)
                            } else {
                                onTagSelected(tag)
                            }
                        },
                        label = { Text(tag.name) },
                        leadingIcon =
                            if (isSelected) {
                                {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                                    )
                                }
                            } else {
                                null
                            },
                        colors =
                            FilterChipDefaults.filterChipColors(
                                labelColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                containerColor = NeeGongNaeGongTheme.colorScheme.background,
                                selectedLabelColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                selectedContainerColor = NeeGongNaeGongTheme.colorScheme.blue,
                            ),
                    )
                }
            }

            HorizontalDivider(
                modifier =
                    Modifier
                        .padding(vertical = 10.dp),
                color = NeeGongNaeGongTheme.colorScheme.gray3,
            )

            // 스터디 설명
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = stringResource(R.string.studies_edit_description),
                style = NeeGongNaeGongTheme.typography.titleSmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
            val descriptionMaxLength = 200
            OutlinedTextField(
                value = description,
                onValueChange = {
                    if (it.length <= descriptionMaxLength) {
                        onDescriptionChanged(it)
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                placeholder = {
                    Text(
                        text = "스터디 설명을 입력하세요",
                        style = NeeGongNaeGongTheme.typography.bodySmall,
                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = NeeGongNaeGongTheme.typography.bodySmall,
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = "${description.length} / $descriptionMaxLength",
                            style = NeeGongNaeGongTheme.typography.bodySmall,
                            color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        )
                    }
                },
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        unfocusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        cursorColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                        focusedBorderColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                        selectionColors =
                            TextSelectionColors(
                                handleColor = NeeGongNaeGongTheme.colorScheme.blue,
                                backgroundColor = NeeGongNaeGongTheme.colorScheme.blue.copy(alpha = 0.3f),
                            ),
                    ),
            )

            HorizontalDivider(
                modifier =
                    Modifier
                        .padding(vertical = 10.dp),
                color = NeeGongNaeGongTheme.colorScheme.gray3,
            )

            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = stringResource(R.string.studies_edit_add_photo),
                style = NeeGongNaeGongTheme.typography.titleSmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                StudiesImagePicker(
                    modifier = Modifier,
                    selectedImageUri = selectedImageUri,
                    profileImage = profileImg,
                    onClick = {
                        photoFromAlbumLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly,
                            ),
                        )
                    },
                    clearSelectedImage = { selectedImageUri = null },
                    clearProfileImage = onClearProfileImg,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 생성하기 버튼
            Button(
                onClick = onCreateStudies,
                enabled = validateCreateStudies,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = NeeGongNaeGongTheme.colorScheme.blue,
                        contentColor = NeeGongNaeGongTheme.colorScheme.background,
                        disabledContainerColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        disabledContentColor = NeeGongNaeGongTheme.colorScheme.gray3,
                    ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.studies_edit_update),
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesComponentScreen() {
    NeeGongNaeGongTheme {
        StudiesEditScreen(
            modifier = Modifier,
            name = "",
            isPublic = true,
            targetStudyTime = 60 * 60 * 7,
            currentMembers = 2,
            maxMembers = 10,
            selectedCategory = null,
            categories = emptyList(),
            selectedTags = emptyList(),
            tags = emptyList(),
            description = "",
            profileImg = "",
            validateCreateStudies = true,
            onNameChanged = {},
            onIsPublicChanged = {},
            onTargetStudyTimeChanged = {},
            onMaxMembersChanged = {},
            onCategorySelected = {},
            onTagSelected = {},
            onTagUnSelected = {},
            onDescriptionChanged = {},
            onClearProfileImg = {},
            onSelectedImage = { _, _ -> },
            onSelectedImageRequest = {},
            onCreateStudies = {},
            popBackStack = {},
        )
    }
}
