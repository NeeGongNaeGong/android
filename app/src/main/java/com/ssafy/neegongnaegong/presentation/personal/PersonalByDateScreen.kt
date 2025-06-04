package com.ssafy.neegongnaegong.presentation.personal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.presentation.component.picker.date.DatePicker
import com.ssafy.neegongnaegong.presentation.component.picker.date.DatePickerState
import com.ssafy.neegongnaegong.presentation.component.picker.date.rememberDatePickerState
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordList
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun PersonalByDateScreen(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    selectedDate: String,
    selectedRecordsByDate: List<LearningRecord>,
    onDateSelected: (String) -> Unit,
    navigateToEditScreen: (Long) -> Unit,
    // Paging3
    onLoadMore: () -> Unit,
    hasDateDataNext: Boolean,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DatePicker(
            modifier = Modifier.fillMaxWidth(),
            state = datePickerState,
            onDateSelected = { date ->
                onDateSelected(date.toString())
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedRecordsByDate.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$selectedDate 에는 공부한 기록이 없습니다.",
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                        ),
                )
            }
        } else {
            StudyRecordList(
                modifier = Modifier.fillMaxSize(),
                learningRecords = selectedRecordsByDate,
                onClick = navigateToEditScreen,
                onLoadMore = onLoadMore,
                hasNext = hasDateDataNext,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalByDateScreenPreview() {
    NeeGongNaeGongTheme {
        PersonalByDateScreen(
            datePickerState = rememberDatePickerState(),
            selectedDate = "2024-04-28",
            selectedRecordsByDate = emptyList(),
            onDateSelected = {},
            navigateToEditScreen = {},
            onLoadMore = {},
            hasDateDataNext = false,
        )
    }
}
