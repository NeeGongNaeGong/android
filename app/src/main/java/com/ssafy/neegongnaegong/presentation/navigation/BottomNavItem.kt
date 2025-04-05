package com.ssafy.neegongnaegong.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssafy.neegongnaegong.R

sealed class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int,
    val route: String,
) {
    data object StudiesScreen : BottomNavItem(
        title = R.string.studies,
        icon = R.drawable.ic_bot_nav_outline_studies,
        iconSelected = R.drawable.ic_bot_nav_fill_studies,
        route = AppNavigation.Tab.Studies.route,
    )

    data object PersonalScreen : BottomNavItem(
        title = R.string.personal,
        icon = R.drawable.ic_bot_nav_outline_individual,
        iconSelected = R.drawable.ic_bot_nav_fill_individual,
        route = AppNavigation.Tab.Personal.route,
    )

    data object CalendarScreen : BottomNavItem(
        title = R.string.calendar,
        icon = R.drawable.ic_bot_nav_outline_calendar,
        iconSelected = R.drawable.ic_bot_nav_fill_calendar,
        route = AppNavigation.Tab.Calendar.route,
    )

    data object ProfileScreen : BottomNavItem(
        title = R.string.profile,
        icon = R.drawable.ic_bot_nav_outline_profile,
        iconSelected = R.drawable.ic_bot_nav_fill_profile,
        route = AppNavigation.Tab.Profile.createRoute(-1),
    )

    companion object {
        val itemList = listOf(
            StudiesScreen,
            PersonalScreen,
            CalendarScreen,
            ProfileScreen,
        )
    }
}
