package com.ssafy.neegongnaegong.presentation.personal

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.personal.component.StudyRecordList
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagList
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun PersonalRoute(
    modifier: Modifier = Modifier,
    viewModel: PersonalViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {

    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PersonalContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        studyRecords = uiState.value.studyRecords,
        onTagPlusClicked = { viewModel.setEvent(PersonalContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(PersonalContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(PersonalContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(PersonalContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = { viewModel.setEvent(PersonalContract.Event.OnSearchTextChanged(it)) },
        onTagSelected = { viewModel.setEvent(PersonalContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(PersonalContract.Event.OnTagDeselected(it)) },
    )

}

@Composable
fun PersonalContent(
    modifier: Modifier = Modifier,
    effect: Flow<PersonalContract.Effect>,
    uiState: PersonalContract.State,
    // study
    studyRecords: List<StudyRecord>,
    // tag
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
            onCancel = onDialogClosed,
            onConfirm = onDialogConfirmed,
            onSearchQueryChanged = onSearchQueryChanged,
            onTagSelected = onTagSelected,
            onTagDeselected = onTagDeselected,
        )
    }

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is PersonalContract.Effect.ShowErrorToast -> {}

                is PersonalContract.Effect.ShowTagLimitExceededToast -> {
                    Toast.makeText(context, "태그는 최대 5개만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    PersonalScreen(
        modifier = modifier,
        studyRecords = uiState.studyRecords,
        //tag
        tags = uiState.tags,
        onTagPlusClicked = onTagPlusClicked,
        onTagEraseClicked = onTagEraseClicked,
    )
}


@Composable
fun PersonalScreen(
    modifier: Modifier = Modifier,
    studyRecords: List<StudyRecord>,
    //tag
    tags: List<Tag>,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
) {
    var selectedFilter by remember { mutableStateOf("태그별") }

    val filterOptions = listOf("태그별", "날짜별")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable { expanded = true }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = selectedFilter,
                    fontSize = 24.sp
                )

                Icon(

                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "드롭다운 열기"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                filterOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedFilter = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedFilter == "태그별") {
            Column(modifier = Modifier.fillMaxSize()) {
                TagList(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 16.dp),
                    tags = tags,
                    onTagPlusClicked = onTagPlusClicked,
                    onTagEraseClicked = onTagEraseClicked
                )

                StudyRecordList(
                    modifier = Modifier.weight(1f),
                    studyRecords = studyRecords
                )
            }
        } else {
            Text("날짜", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            StudyRecordList(
                modifier = Modifier.fillMaxSize(),
                studyRecords = studyRecords
            )
        }
    }
}
