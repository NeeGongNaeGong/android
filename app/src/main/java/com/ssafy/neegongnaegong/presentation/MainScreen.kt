package com.ssafy.neegongnaegong.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.presentation.navigation.BottomNavigationBar
import com.ssafy.neegongnaegong.presentation.navigation.MainNavigationGraph
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavigationGraph(navController = navController)
        }
    }
}


// 각 화면에 대한 Composable 함수들
@Composable
fun StudiesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "스터디 화면",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
fun PersonalScreen() {
    val dummyRecords = listOf(
        StudyRecord("청산별곡 정주행", "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ", "2025-04-14T04:33:02.856Z", "2025-04-14T06:33:02.856Z", listOf("CS", "네트워크")),
        StudyRecord("영어 단어 영어 단어 영어 단어", "VOCA 2200 암기", "2025-04-14T06:33:02.856Z", "2025-04-14T08:33:02.856Z", listOf("CS", "운동")),
        StudyRecord("청산별곡 정주행", "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ", "2025-04-14T04:33:02.856Z", "2025-04-14T06:33:02.856Z", listOf("CS", "네트워크")),
        StudyRecord("청산별곡 정주행", "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ", "2025-04-14T04:33:02.856Z", "2025-04-14T06:33:02.856Z", listOf("CS", "네트워크")),
        StudyRecord("청산별곡 정주행", "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ", "2025-04-14T04:33:02.856Z", "2025-04-14T06:33:02.856Z", listOf("CS", "네트워크")),
        StudyRecord("청산별곡 정주행", "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ", "2025-04-14T04:33:02.856Z", "2025-04-14T06:33:02.856Z", listOf("CS", "네트워크")),
        StudyRecord("청산별곡 정주행", "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ", "2025-04-14T04:33:02.856Z", "2025-04-14T06:33:02.856Z", listOf("CS", "네트워크")),
        )
    com.ssafy.neegongnaegong.presentation.personal.PersonalScreen(
        modifier = Modifier.fillMaxSize(),
        studyRecords = dummyRecords
    )
}

@Composable
fun CalendarScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "캘린더 화면",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "마이페이지 화면",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    NeeGongNaeGongTheme {
        MainScreen()
    }
}
