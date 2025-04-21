package com.ssafy.neegongnaegong.presentation.group.vote

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.group.vote.component.OptionButton
import com.ssafy.neegongnaegong.presentation.group.vote.component.TimePickerDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors

@Composable
fun VoteRoute(
    popBackStack: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { VoteTopBar(popBackStack) },
    ) { paddingValues ->
        VoteContent(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteTopBar(
    popBackStack: () -> Boolean
) {
    // title을 가운데로 위치시켜주는 CenterAlignedTopAppBar
    // https://stackoverflow.com/questions/67497414/how-to-align-title-at-layout-center-in-topappbar
    CenterAlignedTopAppBar(
        title = { Text("투표 만들기") },
        navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기 버튼"
                )
            }
        },
        actions = {
            TextButton(onClick = { popBackStack() }) {
                Text(
                    color = LightColors.Black,
                    text = "완료"
                )
            }
        }
    )
}

@Composable
fun VoteContent(
    modifier: Modifier = Modifier,
) {
    VoteScreen(modifier)
}

@Composable
fun VoteScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .background(LightColors.White)
            .padding(horizontal = 13.dp)
    ) {
        item {
            MainOption()
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
        }
        item {
            EndOption()
        }
    }
}

@Composable
fun MainOption() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LightColors.Gray2)
            .padding(13.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        VoteList()
        VoteOption()
        // 필요한지 확신이 안들어서 숨김 처리
//        HorizontalDivider()
//        Button(
//            enabled = true,
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent,
//                contentColor = LightColors.Black,
//                disabledContentColor = LightColors.Gray2
//            ),
//            onClick = {}) {
//            Text("투표 추가하기")
//        }
    }
}

@Composable
fun VoteList() {
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(LightColors.White)

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    )

    TextField(
        modifier = textFieldModifier,
        colors = textFieldColors,
        singleLine = true,
        value = "", onValueChange = {}, placeholder = { Text("투표 제목") }
    )

    TextField(
        modifier = textFieldModifier,
        colors = textFieldColors,
        singleLine = true,
        value = "", onValueChange = {}, placeholder = { Text("항목 입력") }
    )

    TextField(
        modifier = textFieldModifier,
        colors = textFieldColors,
        singleLine = true,
        value = "", onValueChange = {}, placeholder = { Text("항목 입력") }
    )

    TextField(
        modifier = textFieldModifier,
        colors = textFieldColors,
        singleLine = true,
        value = "", onValueChange = {}, placeholder = { Text("항목 입력") }
    )

    IconButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(LightColors.White)
    ) {
        Icon(
            Icons.Rounded.Add,
            modifier = Modifier.size(48.dp),
            contentDescription = "항목 추가",
            tint = LightColors.Gray4
        )
    }
}

@Composable
fun VoteOption() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LightColors.Gray2)
    ) {
        OptionButton(false, "복수선택") {}
        OptionButton(false, "익명투표") {}
        OptionButton(false, "선택 항목 추가 허용") {}
    }
}

@Composable
fun EndOption() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LightColors.Gray2)
            .padding(13.dp)
    ) {
        var showDatePicker by remember { mutableStateOf(false) }
        var showTimePicker by remember { mutableStateOf(false) }

        LoadDialog(
            showDatePicker,
            showTimePicker,
            { showDatePicker = false },
            { showTimePicker = false })


        OptionButton(false, "종료 시간") {}

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(LightColors.White)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                onClick = {
                    showDatePicker = true
                }
            ) {
                Text(
                    color = LightColors.Black,
                    text = "2025년 1월 27일 (월)"
                )
            }

            TextButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(LightColors.White)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                onClick = { showTimePicker = true }) {
                Text(
                    color = LightColors.Black,
                    text = "오후 4:30"
                )
            }

        }
        OptionButton(false, "종료 30분 전 알림") {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadDialog(
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    onDatePickerClick: () -> Unit,
    onTimePickerClick: () -> Unit
) {
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDatePickerClick,
            confirmButton = { TextButton(onClick = onDatePickerClick) { Text("확인") } },
            dismissButton = { TextButton(onClick = onDatePickerClick) { Text("취소") } },
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
            )
        ) {
            DatePicker(
                colors = DatePickerDefaults.colors(containerColor = Color.White),
                state = rememberDatePickerState(
                    selectableDates = object : SelectableDates {
                        // 오늘 이전 날짜는 선택할 수 없도록 설정
                        override fun isSelectableDate(utcTimeMillis: Long): Boolean {

                            val today = Calendar.getInstance().apply {
                                set(Calendar.HOUR_OF_DAY, 0)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }.timeInMillis

                            return utcTimeMillis >= today
                        }

                        override fun isSelectableYear(year: Int): Boolean {
                            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                            return year >= currentYear
                        }
                    }
                ))
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismiss = onTimePickerClick,
            onConfirm = onTimePickerClick,
        ) {
            TimeInput(
                colors = TimePickerDefaults.colors(containerColor = Color.White),
                state = rememberTimePickerState()
            )
        }
    }
}

@Preview
@Composable
fun PreViewVoteRoute() {
    Scaffold(
        topBar = { VoteTopBar({ false }) },
    ) { paddingValues ->
        VoteContent(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Preview
@Composable
fun PreviewTopAppBar() {
    VoteTopBar(popBackStack = { true })
}

@Preview
@Composable
fun PreviewVoteScreen() {
    VoteScreen()
}

@Preview
@Composable
fun PreviewMainOption() {
    MainOption()
}

@Preview
@Composable
fun PreviewEndOption() {
    EndOption()
}