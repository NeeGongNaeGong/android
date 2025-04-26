package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.ui.theme.DarkColors
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavigationBar(
        modifier =
            modifier
                .wrapContentHeight()
                .navigationBarsPadding()
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                ),
        containerColor =
            when {
                isSystemInDarkTheme() -> DarkColors.DarkBackGround
                else -> LightColors.BackGround
            },
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()


        val currentDestination = navBackStackEntry?.destination

        BottomNavItem.itemList.forEach { screen ->

            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(screen.route::class)
            } == true

            NavigationBarItem(
                interactionSource = NoRippleInteractionSource,
                icon = {
                    Icon(
                        painter =
                            painterResource(
                                id = if (selected) screen.iconSelected else screen.icon,
                            ),
                        contentDescription = stringResource(id = screen.title),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.title),
                        style = if (selected) NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 12.sp) else NeeGongNaeGongTheme.typography.labelSmall,
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 70)
@Composable
fun PreviewBottomNavigationBar() {
    NeeGongNaeGongTheme {
        BottomNavigationBar(
            navController = rememberNavController(),
        )
    }
}

/**
 * 클릭시 알약 파장 효과를 제거.
 */
private object NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}
