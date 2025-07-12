package com.ssafy.neegongnaegong.presentation.group.create

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.FileUtils.getFileExtension
import com.ssafy.neegongnaegong.presentation.util.FileUtils.uriToRequestBody
import com.ssafy.neegongnaegong.presentation.util.TimeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.RequestBody


@Composable
fun StudiesCreateRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesCreateViewModel = hiltViewModel(),
    navigateToMyStudies: () -> Unit,
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesCreateContract.Event.OnLoad)
    }

    StudiesCreateContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onNameChanged = { viewModel.setEvent(StudiesCreateContract.Event.OnNameChanged(it)) },
        onIsPublicChanged = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnIsPublicChanged(it),
            )
        },
        onTargetStudyTimeChanged = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnTargetStudyTimeChanged(it),
            )
        },
        onMaxMembersChanged = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnMaxMembersChanged(it),
            )
        },
        onCategorySelected = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnSelectedCategory(it),
            )
        },
        onTagSelected = { viewModel.setEvent(StudiesCreateContract.Event.OnTagSelected(it)) },
        onTagUnSelected = { viewModel.setEvent(StudiesCreateContract.Event.OnTagUnSelected(it)) },
        onDescriptionChanged = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnDescriptionChanged(it),
            )
        },
        onSelectedImage = { uri, ext ->
            viewModel.setEvent(
                StudiesCreateContract.Event.OnSelectedImage(imageUri = uri, extension = ext),
            )
        },
        onSelectedImageRequest = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnSelectedImageRequest(it),
            )
        },
        onCreateStudies = {
            viewModel.setEvent(
                StudiesCreateContract.Event.OnCreateStudiesClicked,
            )
        },
        navigateToMyStudies = navigateToMyStudies,
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesCreateContent(
    modifier: Modifier = Modifier,
    effect: Flow<StudiesCreateContract.Effect>,
    uiState: StudiesCreateContract.State,
    onNameChanged: (String) -> Unit,
    onIsPublicChanged: (Boolean) -> Unit,
    onTargetStudyTimeChanged: (Int) -> Unit,
    onMaxMembersChanged: (Int) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagUnSelected: (Tag) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSelectedImage: (Uri, String?) -> Unit,
    onSelectedImageRequest: (RequestBody?) -> Unit,
    onCreateStudies: () -> Unit,
    navigateToMyStudies: () -> Unit,
    popBackStack: () -> Unit,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                StudiesCreateContract.Effect.NavigateToBack -> backDispatcher?.onBackPressed()
                StudiesCreateContract.Effect.NavigateToMyStudies -> navigateToMyStudies()
            }
        }
    }
    StudiesCreateScreen(
        modifier = modifier,
        name = uiState.studyInfo.name,
        isPublic = uiState.studyInfo.isPublic,
        targetStudyTime = uiState.studyInfo.targetStudyTime,
        maxMembers = uiState.studyInfo.maxMembers,
        selectedCategory = uiState.selectedCategory,
        categories = uiState.categories,
        selectedTags = uiState.selectedTags,
        tags = uiState.tags,
        description = uiState.studyInfo.description,
        validateCreateStudies = uiState.validateCreateStudies,
        onNameChanged = onNameChanged,
        onIsPublicChanged = onIsPublicChanged,
        onTargetStudyTimeChanged = onTargetStudyTimeChanged,
        onMaxMembersChanged = onMaxMembersChanged,
        onCategorySelected = onCategorySelected,
        onTagSelected = onTagSelected,
        onTagUnSelected = onTagUnSelected,
        onDescriptionChanged = onDescriptionChanged,
        onSelectedImage = onSelectedImage,
        onSelectedImageRequest = onSelectedImageRequest,
        onCreateStudies = onCreateStudies,
        popBackStack = popBackStack,
    )

    if (uiState.isOnCreate) LoadingDialog()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StudiesCreateScreen(
    modifier: Modifier = Modifier,
    name: String,
    isPublic: Boolean,
    targetStudyTime: Int,
    maxMembers: Int,
    selectedCategory: Category?,
    categories: List<Category>,
    selectedTags: List<Tag>,
    tags: List<Tag>,
    description: String,
    validateCreateStudies: Boolean,
    onNameChanged: (String) -> Unit,
    onIsPublicChanged: (Boolean) -> Unit,
    onTargetStudyTimeChanged: (Int) -> Unit,
    onMaxMembersChanged: (Int) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagUnSelected: (Tag) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSelectedImage: (Uri, String?) -> Unit,
    onSelectedImageRequest: (RequestBody?) -> Unit,
    onCreateStudies: () -> Unit,
    popBackStack: () -> Unit = {},
) {
    val context = LocalContext.current
    var targetStudyTimeDropdown by remember { mutableStateOf(false) }
    var maxMembersDropdown by remember { mutableStateOf(false) }
    var categoryDropdown by remember { mutableStateOf(false) }
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
                    text = stringResource(R.string.studies_create_title),
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
                text = stringResource(R.string.studies_create_study_name),
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
                    text = stringResource(R.string.studies_create_public_private),
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
                    text = stringResource(R.string.studies_create_study_time),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Box {
                    OutlinedCard(
                        modifier =
                            Modifier
                                .width(120.dp)
                                .clickable { targetStudyTimeDropdown = true },
                        shape = RoundedCornerShape(8.dp),
                        colors =
                            CardColors(
                                containerColor = NeeGongNaeGongTheme.colorScheme.background,
                                contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                            ),
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                                    .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "주 ${targetStudyTime / TimeUnit.HOUR.seconds} 시간",
                                style = NeeGongNaeGongTheme.typography.bodySmall,
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "선택하기",
                                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = targetStudyTimeDropdown,
                        onDismissRequest = { targetStudyTimeDropdown = false },
                        modifier =
                            Modifier
                                .width(120.dp)
                                .heightIn(max = 200.dp)
                                .background(color = NeeGongNaeGongTheme.colorScheme.gray2),
                    ) {
                        for (hour in 1..50) {
                            DropdownMenuItem(
                                text = { Text("주 ${hour}시간") },
                                onClick = {
                                    onTargetStudyTimeChanged((hour * TimeUnit.HOUR.seconds).toInt())
                                    targetStudyTimeDropdown = false
                                },
                                colors =
                                    MenuDefaults.itemColors(
                                        textColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                        disabledTextColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                                    ),
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.studies_create_max_members),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Box {
                    OutlinedCard(
                        modifier =
                            Modifier
                                .width(120.dp)
                                .clickable { maxMembersDropdown = true },
                        shape = RoundedCornerShape(8.dp),
                        colors =
                            CardColors(
                                containerColor = NeeGongNaeGongTheme.colorScheme.background,
                                contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                            ),
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                                    .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "$maxMembers 명",
                                style = NeeGongNaeGongTheme.typography.bodyLarge,
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "선택하기",
                                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = maxMembersDropdown,
                        onDismissRequest = { maxMembersDropdown = false },
                        modifier =
                            Modifier
                                .width(120.dp)
                                .heightIn(max = 200.dp)
                                .background(color = NeeGongNaeGongTheme.colorScheme.gray2),
                    ) {
                        for (memberCount in 2..30) {
                            DropdownMenuItem(
                                text = { Text("$memberCount 명") },
                                onClick = {
                                    onMaxMembersChanged(memberCount)
                                    maxMembersDropdown = false
                                },
                                colors =
                                    MenuDefaults.itemColors(
                                        textColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                        disabledTextColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                                    ),
                            )
                        }
                    }
                }
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
                    text = stringResource(R.string.studies_create_select_category),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                Box {
                    OutlinedCard(
                        modifier =
                            Modifier
                                .width(120.dp)
                                .clickable { categoryDropdown = true },
                        shape = RoundedCornerShape(8.dp),
                        colors =
                            CardColors(
                                containerColor = NeeGongNaeGongTheme.colorScheme.background,
                                contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                disabledContainerColor = Color.Red,
                                disabledContentColor = Color.Blue,
                            ),
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                                    .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = selectedCategory?.name ?: "선택",
                                style = NeeGongNaeGongTheme.typography.bodyLarge,
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "선택하기",
                                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = categoryDropdown,
                        onDismissRequest = { categoryDropdown = false },
                        modifier =
                            Modifier
                                .width(120.dp)
                                .heightIn(max = 200.dp)
                                .background(color = NeeGongNaeGongTheme.colorScheme.gray2),
                    ) {
                        for (category in categories) {
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    onCategorySelected(category)
                                    categoryDropdown = false
                                },
                                colors =
                                    MenuDefaults.itemColors(
                                        textColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                        disabledTextColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                                    ),
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // 태그 선택
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = stringResource(R.string.studies_create_select_tags),
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
                text = stringResource(R.string.studies_create_description),
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
                text = stringResource(R.string.studies_create_add_photo),
                style = NeeGongNaeGongTheme.typography.titleSmall,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                // 이미지가 선택되었는지 여부에 따라 다른 UI 표시
                if (selectedImageUri != null) {
                    Box(
                        modifier =
                            Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    photoFromAlbumLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly,
                                        ),
                                    )
                                },
                    ) {
                        // Landscapist-Glide로 선택된 이미지 표시
                        GlideImage(
                            imageModel = { selectedImageUri },
                            modifier = Modifier.fillMaxSize(),
                            loading = {
                                Box(modifier = Modifier.matchParentSize()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center),
                                    )
                                }
                            },
                            failure = {
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxSize()
                                            .background(MaterialTheme.colorScheme.errorContainer),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = "이미지 로드 실패",
                                        modifier = Modifier.align(Alignment.Center),
                                        tint = MaterialTheme.colorScheme.error,
                                    )
                                }
                            },
                        )

                        // 이미지 위에 삭제 버튼
                        Box(
                            modifier =
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(24.dp)
                                    .background(
                                        color =
                                            NeeGongNaeGongTheme.colorScheme.background.copy(
                                                alpha = 0.7f,
                                            ),
                                        shape = CircleShape,
                                    )
                                    .clickable { selectedImageUri = null },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "이미지 삭제",
                                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                } else {
                    // 기존 이미지 추가 버튼
                    Box(
                        modifier =
                            Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NeeGongNaeGongTheme.colorScheme.blue)
                                .clickable {
                                    photoFromAlbumLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly,
                                        ),
                                    )
                                },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.studies_create_add_photo),
                            tint = NeeGongNaeGongTheme.colorScheme.background,
                            modifier = Modifier.size(64.dp),
                        )
                    }
                }
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
                    text = stringResource(R.string.studies_create_create),
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesManagementScreen() {
    NeeGongNaeGongTheme {
        StudiesCreateScreen(
            modifier = Modifier,
            name = "",
            isPublic = true,
            targetStudyTime = 60 * 60 * 7,
            maxMembers = 10,
            selectedCategory = null,
            categories = emptyList(),
            selectedTags = emptyList(),
            tags = emptyList(),
            description = "",
            validateCreateStudies = true,
            onNameChanged = {},
            onIsPublicChanged = {},
            onTargetStudyTimeChanged = {},
            onMaxMembersChanged = {},
            onCategorySelected = {},
            onTagSelected = {},
            onTagUnSelected = {},
            onDescriptionChanged = {},
            onSelectedImage = { _, _ -> },
            onSelectedImageRequest = {},
            onCreateStudies = {},
            popBackStack = {},
        )
    }
}
