package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.component.button.GradientFloatingActionButton
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onFabClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxWidth().height(60.dp)) {
        NavigationBar(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight()
                    .navigationBarsPadding()
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                    ),
            containerColor = NeeGongNaeGongTheme.colorScheme.background,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            BottomNavItem.itemList.forEachIndexed { index, screen ->
                if (index == BottomNavItem.itemList.size / 2) {
                    Spacer(modifier = Modifier.width(72.dp))
                }

                val selected =
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(screen.route::class)
                    } == true

                NavigationBarItem(
                    interactionSource = NoRippleInteractionSource,
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter =
                                painterResource(
                                    id = if (selected) screen.iconSelected else screen.icon,
                                ),
                            contentDescription = stringResource(id = screen.title),
                            tint =
                                if (selected) {
                                    NeeGongNaeGongTheme.colorScheme.primaryText
                                } else {
                                    NeeGongNaeGongTheme.colorScheme.secondaryText
                                },
                        )
                    },
                    label = {
                        val labelDp: Dp = 10.dp
                        val labelSp: TextUnit =
                            with(LocalDensity.current) {
                                labelDp.toSp()
                            }
                        Text(
                            text = stringResource(id = screen.title),
                            style =
                                if (selected) {
                                    NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = labelSp)
                                } else {
                                    NeeGongNaeGongTheme.typography.labelSmall.copy(fontSize = labelSp)
                                },
                            color =
                                if (selected) {
                                    NeeGongNaeGongTheme.colorScheme.primaryText
                                } else {
                                    NeeGongNaeGongTheme.colorScheme.secondaryText
                                },
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
        val brushColor =
            listOf(
                NeeGongNaeGongTheme.colorScheme.blue,
                NeeGongNaeGongTheme.colorScheme.mintBlue,
            )
        GradientFloatingActionButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = onFabClick,
            shape = CircleShape,
            containerColor = NeeGongNaeGongTheme.colorScheme.background,
            brushColor = Brush.horizontalGradient(colors = brushColor),
            elevation = FloatingActionButtonDefaults.elevation(1.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bot_nav_study),
                contentDescription = "공부",
                tint = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.9f),
                modifier =
                    Modifier
                        .size(56.dp)
                        .padding(4.dp),
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 70)
@NeeGongNaeGongPreviews
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

    override suspend fun emit(interaction: Interaction) = Unit

    override fun tryEmit(interaction: Interaction) = true
}
