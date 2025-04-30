package com.ssafy.neegongnaegong.presentation.group.management

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

private const val TAG = "StudiesManagementScreen"

@Composable
fun StudiesManagementRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    StudiesManagementContent(
        modifier = modifier,
        popBackStack = popBackStack,
    )
}

@Composable
fun StudiesManagementContent(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
) {
    StudiesManagementScreen(
        modifier = modifier,
        popBackStack = popBackStack,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudiesManagementScreen(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = {},
) {
    var studyName by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }
    var studyDescription by remember { mutableStateOf("") }
    val selectedTags = remember { mutableStateOf(setOf<String>()) }
    var selectedMembers by remember { mutableIntStateOf(10) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val photoFromAlbumLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let {
                    selectedImageUri = it
                    val encodeUri = Uri.encode(it.toString())
                    Log.d(TAG, "StudiesComponentScreen: $encodeUri")
                }
            },
        )

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = stringResource(R.string.studies_management_title),
                        style = NeeGongNaeGongTheme.typography.titleMedium,
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
                // 타이틀은 호출하는 쪽에서 넣기로 함

                // 스터디명 섹션
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.studies_management_study_name),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                )

                OutlinedTextField(
                    value = studyName,
                    onValueChange = { studyName = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("스터디 이름을 입력하세요") },
                    singleLine = true,
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // 공개/비공개 설정
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.studies_management_public_private),
                        style = NeeGongNaeGongTheme.typography.titleSmall,
                    )
                    Switch(
                        checked = isPublic,
                        onCheckedChange = { isPublic = it },
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.studies_management_max_members),
                        style = NeeGongNaeGongTheme.typography.titleSmall,
                    )

                    Box {
                        OutlinedCard(
                            modifier =
                                Modifier
                                    .width(120.dp)
                                    .clickable { isDropdownExpanded = true },
                            shape = RoundedCornerShape(8.dp),
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
                                    text = "$selectedMembers 명",
                                    style = NeeGongNaeGongTheme.typography.bodyLarge,
                                )

                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "선택하기",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier =
                                Modifier
                                    .width(120.dp)
                                    .heightIn(max = 200.dp),
                        ) {
                            for (i in 1..30) {
                                DropdownMenuItem(
                                    text = { Text("$i 명") },
                                    onClick = {
                                        selectedMembers = i
                                        isDropdownExpanded = false
                                    },
                                )
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                // TODO : 임시 카테고리
                // 카테고리 선택
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.studies_management_select_category),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
                    val categories = listOf("프로그래밍", "외국어", "취업준비", "자격증")
                    categories.forEach { category ->
                        var isSelected by remember { mutableStateOf(false) }
                        TagChip(
                            text = category,
                            isSelected = isSelected,
                            onClick = { isSelected = !isSelected },
                        )
                    }
                }
                // TODO : 임시 태그
                // 태그 선택
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.studies_management_select_tags),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 5.dp),
                ) {
                    selectedTags.value.forEach { tag ->
                        TagChip(
                            text = tag,
                            isSelected = true,
                            onClick = {
                                selectedTags.value -= tag
                            },
                        )
                    }

                    // 태그 추가 버튼
                    Box(
                        modifier =
                            Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray.copy(alpha = 0.5f))
                                .clickable {
                                    // TODO: 태그 추가 다이얼로그 표시
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "태그 추가",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // 스터디 설명
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.studies_management_description),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                )

                OutlinedTextField(
                    value = studyDescription,
                    onValueChange = { studyDescription = it },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                    placeholder = { Text("스터디 설명을 입력하세요") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = 3,
                    textStyle = NeeGongNaeGongTheme.typography.bodySmall,
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.studies_management_add_photo),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
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
                                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                                            shape = CircleShape,
                                        )
                                        .clickable { selectedImageUri = null },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "이미지 삭제",
                                    tint = MaterialTheme.colorScheme.onSurface,
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
                                    .background(MaterialTheme.colorScheme.primary)
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
                                contentDescription = stringResource(R.string.studies_management_add_photo),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(24.dp))

                // 생성하기 버튼
                Button(
                    onClick = { /* TODO : 그룹 생성 및 수정 */ },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.studies_management_create),
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TagChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.LightGray.copy(alpha = 0.5f)
                    },
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color =
                if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            style = NeeGongNaeGongTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStudiesComponentScreen() {
    NeeGongNaeGongTheme {
        StudiesManagementScreen()
    }
}
