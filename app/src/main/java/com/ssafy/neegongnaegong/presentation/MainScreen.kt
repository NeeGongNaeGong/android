package com.ssafy.neegongnaegong.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.group.StudiesRoute
import com.ssafy.neegongnaegong.presentation.navigation.BottomNavItem
import com.ssafy.neegongnaegong.presentation.navigation.BottomNavigationBar
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
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.StudiesScreen.route,
    ) {
        composable(BottomNavItem.StudiesScreen.route) {
//            StudiesScreen()
            StudiesRoute(
                modifier = Modifier,
                popBackStack = { },
            )
        }
        composable(BottomNavItem.PersonalScreen.route) {
            PersonalScreen()
//            StudiesRoute(
//                modifier = Modifier,
//                popBackStack = { },
//            )
        }
        composable(BottomNavItem.CalendarScreen.route) {
            CalendarScreen()
        }
        composable(BottomNavItem.ProfileScreen.route) {
            ProfileScreen()
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
        MainScreen()
    }
}
