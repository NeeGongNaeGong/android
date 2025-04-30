package com.ssafy.neegongnaegong.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.group.component.drawer.StudiesDrawer
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import com.ssafy.neegongnaegong.presentation.navigation.BottomNavigationBar
import com.ssafy.neegongnaegong.presentation.navigation.MainNavigationGraph
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.StudiesDrawerController

private const val TAG = "MainScreen"

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomNavigationBar =
        currentDestination?.hierarchy?.any {
            it.hasRoute(AppNavigation.Tab.Auth::class)
        } == false

    val isStudiesDrawerOpen by StudiesDrawerController.isOpen.collectAsState()
    val studiesDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(isStudiesDrawerOpen) {
        if (isStudiesDrawerOpen) {
            studiesDrawerState.open()
        } else {
            studiesDrawerState.close()
        }
    }

    LaunchedEffect(studiesDrawerState.currentValue) {
        if (studiesDrawerState.isOpen) {
            StudiesDrawerController.open()
        } else {
            StudiesDrawerController.close()
        }
    }

    val currentTabRoute = currentDestination?.route
    val enableGestures = remember { mutableStateOf(false) }

    LaunchedEffect(currentTabRoute) {
        enableGestures.value = currentDestination?.hierarchy?.any {
            it.hasRoute(AppNavigation.Screen.Studies.StudiesDetail::class)
        } == true
    }

    ModalNavigationDrawer(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        drawerState = studiesDrawerState,
        gesturesEnabled = enableGestures.value,
        drawerContent = {
            StudiesDrawer(
                headerImageUrl = null,
                onGroupManagementClick = {},
                onMemberManagementClick = {},
                onScheduleManagementClick = {},
                onStudyCreateClick = {},
                onStudySearchClick = {},
                onMyStudyClick = {},
                onStudyItemClick = {},
            )
        },
    ) {
        Scaffold(
            bottomBar = {
                if (showBottomNavigationBar) BottomNavigationBar(navController = navController)
            },
            containerColor = NeeGongNaeGongTheme.colorScheme.background
        ) { innerPadding ->
            // Scaffold에서 계산해서 내려준 innerPadding 값을 사용하고, 이걸 사용했다고 명시하여서, Box 하위의 Composable에서
            // 시스템적으로 패딩을 계산할 때 여기에 사용된 Padding을 중복 사용하지 않도록 함
            // 다른 화면의 Scaffold에서 사용된 값은 빼고서 계산해줌
            Box(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
            ) {
                MainNavigationGraph(navController = navController)
            }
        }
    }
}

// 각 화면에 대한 Composable 함수들
@Composable
fun StudiesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "스터디 화면",
            style = NeeGongNaeGongTheme.typography.titleMedium,
        )
    }
}

@Composable
fun PersonalScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "개인 화면",
            style = NeeGongNaeGongTheme.typography.titleMedium,
        )
    }
}

@Composable
fun CalendarScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "캘린더 화면",
            style = NeeGongNaeGongTheme.typography.titleMedium,
        )
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "마이페이지 화면",
            style = NeeGongNaeGongTheme.typography.titleMedium,
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
