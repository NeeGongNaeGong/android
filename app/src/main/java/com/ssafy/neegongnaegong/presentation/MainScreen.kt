package com.ssafy.neegongnaegong.presentation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.presentation.base.UpdateStatusViewModel
import com.ssafy.neegongnaegong.presentation.common.LocalDrawerState
import com.ssafy.neegongnaegong.presentation.component.InAppUpdateDialog
import com.ssafy.neegongnaegong.presentation.component.snackbar.NeeGongNaeGongSnackbarHost
import com.ssafy.neegongnaegong.presentation.group.component.drawer.StudiesDrawerContent
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import com.ssafy.neegongnaegong.presentation.navigation.BottomNavigationBar
import com.ssafy.neegongnaegong.presentation.navigation.MainNavigationGraph
import com.ssafy.neegongnaegong.presentation.personal.PersonalContract
import com.ssafy.neegongnaegong.presentation.personal.PersonalViewModel
import com.ssafy.neegongnaegong.presentation.timer.TimerActivity
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.openPlayStoreForUpdate
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    startDestination: AppNavigation.Tab,
    updateStatusViewModel: UpdateStatusViewModel = hiltViewModel(),
) {
    val updateStatus by updateStatusViewModel.updateStatus.collectAsStateWithLifecycle()
    // 업데이트 다이얼로그를 띄울지 결정하는 상태
    var showUpdateDialog by remember { mutableStateOf(false) }

    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val needRefresh = result.data?.getBooleanExtra("needRefreshRecordList", false) ?: false
            if (needRefresh) {
                val navBackStackEntry = navController.currentBackStackEntry
                val destination = navBackStackEntry?.destination

                val isInPersonalTab =
                    destination?.hierarchy?.any {
                        it.hasRoute(AppNavigation.Tab.Personal::class)
                    } == true

                navBackStackEntry?.let {
                    if (isInPersonalTab) {
                        val viewModelStoreOwner = navBackStackEntry as ViewModelStoreOwner
                        val personalViewModel =
                            ViewModelProvider(viewModelStoreOwner)[PersonalViewModel::class.java]
                        personalViewModel.setEvent(PersonalContract.Event.OnRecordRefresh)
                    }
                }
            }
        }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomNavigationBar =
        currentDestination?.let { destination ->
            val isAuthTab: Boolean =
                destination.hierarchy
                    .any { navDestination: NavDestination ->
                        navDestination.hasRoute(AppNavigation.Tab.Auth::class)
                    }

            val isEditScreen: Boolean =
                destination.hierarchy
                    .any { navDestination: NavDestination ->
                        navDestination.hasRoute(AppNavigation.Screen.Personal.Edit::class)
                    }

            val isNotificationScreen: Boolean =
                destination.hierarchy
                    .any { navDestination: NavDestination ->
                        navDestination.hasRoute(AppNavigation.Screen.Profile.Notification::class)
                    }

            !isAuthTab && !isEditScreen && !isNotificationScreen
        } != false

    val studiesDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val context = LocalContext.current

    val enableGestures = remember { mutableStateOf(false) }

    LaunchedEffect(studiesDrawerState.currentValue) {
        enableGestures.value = studiesDrawerState.isOpen
    }

    // 앱이 처음 시작될 때 업데이트가 있는지 '정보'만 확인
    LaunchedEffect(updateStatus) {
        if (updateStatus.updateAvailability) {
            showUpdateDialog = true
        }
    }

    // 업데이트 다이얼로그
    if (showUpdateDialog) {
        InAppUpdateDialog(
            onUpdateClick = {
                // 업데이트 버튼 누르면 플레이스토어로 이동
                openPlayStoreForUpdate(context)
                showUpdateDialog = false
            },
            onDismiss = {
                updateStatusViewModel.skipUpdate(updateStatus.availableVersionCode)
                showUpdateDialog = false
            },
        )
    }

    CompositionLocalProvider(
        LocalDrawerState provides studiesDrawerState,
    ) {
        val currentDrawerState = LocalDrawerState.current
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            modifier =
                Modifier
                    .background(color = NeeGongNaeGongTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.systemBars),
            drawerState = currentDrawerState,
            gesturesEnabled = enableGestures.value,
            drawerContent = {
                Box(modifier = Modifier.fillMaxWidth(0.75f)) {
                    val entry = navBackStackEntry ?: return@Box
                    val route =
                        if (entry.destination.hasRoute(AppNavigation.Screen.Studies.StudiesDetail::class)) {
                            entry.toRoute<AppNavigation.Screen.Studies.StudiesDetail>()
                        } else {
                            null
                        }
                    val navigateAndCloseDrawer = { destination: AppNavigation.Screen.Studies ->
                        navController.navigate(destination)
                        scope.launch { studiesDrawerState.snapTo(DrawerValue.Closed) }
                    }
                    StudiesDrawerContent(
                        navBackStackEntry = entry,
                        navigateTodStudiesEdit = { role ->
                            route?.let { route ->
                                navigateAndCloseDrawer(
                                    AppNavigation.Screen.Studies.Edit(role, route.studyGroupId),
                                )
                            }
                        },
                        navigateToStudiesMembersRole = { role ->
                            route?.let { route ->
                                navigateAndCloseDrawer(
                                    AppNavigation.Screen.Studies.StudiesMembersRole(
                                        role,
                                        route.studyGroupId,
                                    ),
                                )
                            }
                        },
                        navigateToStudiesApplications = { role ->
                            route?.let { route ->
                                navigateAndCloseDrawer(
                                    AppNavigation.Screen.Studies.StudiesApplication(
                                        role,
                                        route.studyGroupId,
                                    ),
                                )
                            }
                        },
                    )
                }
            },
        ) {
            Scaffold(
                snackbarHost = { NeeGongNaeGongSnackbarHost() },
                bottomBar = {
                    if (showBottomNavigationBar) {
                        BottomNavigationBar(
                            navController = navController,
                            onFabClick = {
                                val intent = Intent(context, TimerActivity::class.java)
                                resultLauncher.launch(intent)
                            },
                        )
                    }
                },
                containerColor = NeeGongNaeGongTheme.colorScheme.background,
            ) { innerPadding ->
                // Scaffold에서 계산해서 내려준 innerPadding 값을 사용하고, 이걸 사용했다고 명시하여서, Box 하위의 Composable에서
                // 시스템적으로 패딩을 계산할 때 여기에 사용된 Padding을 중복 사용하지 않도록 함
                // 다른 화면의 Scaffold에서 사용된 값은 빼고서 계산해줌
                Box(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                            .background(NeeGongNaeGongTheme.colorScheme.background),
                ) {
                    MainNavigationGraph(navController = navController, startDestination = startDestination)
                }
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

@NeeGongNaeGongPreviews
@Composable
fun PreviewMainScreen() {
    NeeGongNaeGongTheme {
        MainScreen(rememberNavController(), AppNavigation.Tab.Studies)
    }
}
