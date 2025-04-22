package com.ssafy.neegongnaegong.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import com.ssafy.neegongnaegong.presentation.navigation.BottomNavigationBar
import com.ssafy.neegongnaegong.presentation.navigation.MainNavigationGraph
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun MainScreen(isLoginSuccess: Boolean = false) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomNavigationBar = currentDestination?.hierarchy?.any {
        it.hasRoute(AppNavigation.Login::class)
    } == false

    Scaffold(
        bottomBar = {
            if (showBottomNavigationBar) BottomNavigationBar(navController = navController)
        },
    ) { innerPadding ->
        // Scaffold에서 계산해서 내려준 innerPadding 값을 사용하고, 이걸 사용했다고 명시하여서, Box 하위의 Composable에서
        // 시스템적으로 패딩을 계산할 때 여기에 사용된 Padding을 중복 사용하지 않도록 함
        // 다른 화면의 Scaffold에서 사용된 값은 빼고서 계산해줌
        Box(modifier = Modifier
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)) {
            MainNavigationGraph(isLoginSuccess, navController = navController)
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
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "개인 화면",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
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
        MainScreen(true)
    }
}
