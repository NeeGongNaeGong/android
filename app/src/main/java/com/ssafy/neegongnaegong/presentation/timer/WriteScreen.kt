package com.ssafy.neegongnaegong.presentation.timer

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


// 컴포넌트 분리
// 다이얼로그
// 시간 전달
// 화면 이동
// Route에 뷰모델 담는 이유
// 디테일한 색을 다크,화이트로 바꾸는 방식 찾기

@Composable
fun WriteScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: WriteViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {

    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WriteContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onCancelClicked = { viewModel.setEvent(WriteContract.Event.OnCancelClicked) },
        onConfirmClicked = { viewModel.setEvent(WriteContract.Event.OnConfirmClicked) },
        onTitleChanged = { viewModel.setEvent(WriteContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(WriteContract.Event.OnContentChanged(it)) },
        onTagPlusClicked = { viewModel.setEvent(WriteContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(WriteContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(WriteContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(WriteContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = { viewModel.setEvent(WriteContract.Event.OnSearchTextChanged(it)) },
        onTagSelected = { viewModel.setEvent(WriteContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(WriteContract.Event.OnTagDeselected(it)) },
    )

}


@Composable
fun WriteContent(
    modifier: Modifier = Modifier,
    effect: Flow<WriteContract.Effect>,
    uiState: WriteContract.State,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    onDialogClosed: () -> Unit,
    onDialogConfirmed: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagDeselected: (Tag) -> Unit,
) {
    val context = LocalContext.current

    if (uiState.isDialogShow) {
        TagSelectDialog(
            selectedTags = uiState.selectedTags,
            unSelectedTags = uiState.unSelectedTags,
            onCancel =  onDialogClosed,
            onConfirm = onDialogConfirmed,
            onSearchQueryChanged = onSearchQueryChanged,
            onTagSelected = onTagSelected,
            onTagDeselected = onTagDeselected,
        )
    }

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is WriteContract.Effect.NavigateToHome -> {

                }

                is WriteContract.Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is WriteContract.Effect.ShowSuccessToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    WriteScreen(
        modifier = modifier,
        title = uiState.title,
        content = uiState.content,
        startTime = uiState.startTime,
        endTime = uiState.endTime,
        tags = uiState.tags,
        onTitleChanged = {
            onTitleChanged(it)
        },
        onContentChanged = onContentChanged,
        onTagPlusClicked = onTagPlusClicked,
        onTagEraseClicked = onTagEraseClicked,
        onCancelClicked = onCancelClicked,
        onConfirmClicked = onConfirmClicked,
    )


}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    startTime: Long = 0,
    endTime: Long = 0,
    tags: List<Tag> = emptyList(),
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit = {},
    onTagPlusClicked: () -> Unit = {},
    onTagEraseClicked: (Tag) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 12.dp, end = 12.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "2025년 02월 03일",
                    style = Typography.titleMedium,
                    color = LightColors.Black,
                    fontSize = 20.sp,
                )
                Text(
                    text = "오후 3시 ~ 오후 4시 30분",
                    style = Typography.bodySmall,
                    fontSize = 14.sp,
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title,
                onValueChange = {
                    onTitleChanged(it)
                },
                textStyle = Typography.bodySmall.copy(
                    fontSize = 30.sp,
                    color = LightColors.Black,
                    fontFeatureSettings = "tnum",
                ),
                placeholder = {
                    Text(
                        text = "제목을 입력하세요",
                        color = LightColors.Gray4,
                        fontSize = 30.sp,
                        letterSpacing = 0.5.sp,
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                ),
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.5f),
                value = content,
                onValueChange = onContentChanged,
                textStyle = Typography.labelLarge.copy(
                    fontSize = 18.sp,
                    fontFeatureSettings = "tnum",
                ),
                placeholder = {
                    Text(
                        text = "내용을 입력하세요",
                        fontSize = 18.sp,
                        color = LightColors.Gray4,
                        letterSpacing = 0.2.sp,
                    )
                },
                maxLines = 5,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                )
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = LightColors.Blue
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = tag.koName,
                                fontSize = 16.sp,
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { onTagEraseClicked(tag) },
                                imageVector = Icons.Default.Close,
                                contentDescription = "삭제",
                                tint = Color.White
                            )
                        }
                    }
                }

                Surface(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onTagPlusClicked() },
                    shape = RoundedCornerShape(4.dp),
                    color = LightColors.Blue,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "태그 추가",
                            tint = Color.White
                        )
                    }
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = onCancelClicked,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                border = BorderStroke(0.dp, Color.Transparent),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "취소",
                    style = Typography.titleLarge.copy(
                        fontSize = 16.sp
                    )
                )
            }

            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = onConfirmClicked,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                border = BorderStroke(0.dp, Color.Transparent),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "확인",
                    style = Typography.titleLarge.copy(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}


@Preview
@Composable
private fun PreviewWriteScreen() {
    NeeGongNaeGongTheme {
        Surface {

        }
    }
}