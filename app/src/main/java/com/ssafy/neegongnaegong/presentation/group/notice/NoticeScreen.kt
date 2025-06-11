package com.ssafy.neegongnaegong.presentation.group.notice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun NoticeRoute(
    modifier: Modifier = Modifier,
    popBackStackInclusive: (Int, Long) -> Unit,
    popBackStack: () -> Boolean,
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        TopAppBar(
            title = {
                Text(
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    text = "공지 만들기",
                )
            },
            onNavigationClick = { viewModel.setEvent(NoticeContract.Event.OnClickPopBackStackButton) },
            actionButtons = {
                TextButton(
                    enabled = uiState.title.isNotEmpty() && uiState.content.isNotEmpty(),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = NeeGongNaeGongTheme.colorScheme.gray4,
                        ),
                    onClick = {
                        viewModel.setEvent(
                            NoticeContract.Event.OnClickCompleteButton,
                        )
                    },
                ) {
                    Text(
                        style = NeeGongNaeGongTheme.typography.bodyMedium,
                        text = "완료",
                    )
                }
            },
        )

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    NoticeContract.Effect.NavigateToBackStack -> popBackStack()
                    is NoticeContract.Effect.NavigateToBackStackInclusive ->
                        popBackStackInclusive(
                            effect.startIndex,
                            effect.studyGroupId,
                        )
                }
            }
        }

        NoticeContent(
            modifier
                .fillMaxSize()
                .padding(horizontal = NeeGongNaeGongTheme.paddingScheme.sp3),
            title = uiState.title,
            content = uiState.content,
            onChangeTitle = { viewModel.setEvent(NoticeContract.Event.OnChangeTitle(it)) },
            onChangeContent = { viewModel.setEvent(NoticeContract.Event.OnChangeContent(it)) },
        )
    }
}

@Composable
fun NoticeContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onChangeTitle: (String) -> Unit,
    onChangeContent: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "제목",
            style = NeeGongNaeGongTheme.typography.titleSmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            overflow = TextOverflow.Ellipsis,
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(NeeGongNaeGongTheme.paddingScheme.sp2))
        BasicTextField(
            value = title,
            onValueChange = onChangeTitle,
            modifier =
                Modifier
                    .fillMaxWidth(),
            // 여기선 Text 자체 padding 조절
            textStyle = NeeGongNaeGongTheme.typography.titleSmall,
            cursorBrush = SolidColor(NeeGongNaeGongTheme.colorScheme.primaryText),
        )

        Spacer(modifier = Modifier.height(NeeGongNaeGongTheme.paddingScheme.sp10))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "내용",
            style = NeeGongNaeGongTheme.typography.titleSmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            overflow = TextOverflow.Ellipsis,
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(NeeGongNaeGongTheme.paddingScheme.sp2))
        BasicTextField(
            value = content,
            onValueChange = onChangeContent,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(0.dp),
            // 여기선 Text 자체 padding 조절
            textStyle = NeeGongNaeGongTheme.typography.bodyMedium,
            cursorBrush = SolidColor(NeeGongNaeGongTheme.colorScheme.primaryText),
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNotice() {
    NeeGongNaeGongTheme {
        NoticeContent(content = "dddd", title = "ddd", onChangeTitle = {}, onChangeContent = {})
    }
}
