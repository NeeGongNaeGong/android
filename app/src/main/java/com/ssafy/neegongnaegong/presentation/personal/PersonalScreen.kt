package com.ssafy.neegongnaegong.presentation.personal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.personal.component.StudyRecordItem
import com.ssafy.neegongnaegong.presentation.personal.component.StudyRecordList
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagList


@Composable
fun PersonalRoute() {

}

@Composable
fun PersonalContent() {

}

@Composable
fun PersonalScreen(
    modifier: Modifier = Modifier,
    studyRecords: List<StudyRecord>,
    tags: List<Tag> = emptyList(),
    onTagPlusClicked: () -> Unit = {},
    onTagEraseClicked: (Tag) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
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
            Column {
                TagList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    tags = tags,
                    onTagPlusClicked = onTagPlusClicked,
                    onTagEraseClicked = onTagEraseClicked
                )

                StudyRecordList(
                    modifier = Modifier.fillMaxSize(),
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
