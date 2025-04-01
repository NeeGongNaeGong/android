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
        route = ScreenDestinations.Studies.route,
    )

    data object PersonalScreen : BottomNavItem(
        title = R.string.personal,
        icon = R.drawable.ic_bot_nav_outline_individual,
        iconSelected = R.drawable.ic_bot_nav_fill_individual,
        route = ScreenDestinations.Personal.route,
    )

    data object CalendarScreen : BottomNavItem(
        title = R.string.calendar,
        icon = R.drawable.ic_bot_nav_outline_calendar,
        iconSelected = R.drawable.ic_bot_nav_fill_calendar,
        route = ScreenDestinations.Calendar.route,
    )

    data object ProfileScreen : BottomNavItem(
        title = R.string.profile,
        icon = R.drawable.ic_bot_nav_outline_profile,
        iconSelected = R.drawable.ic_bot_nav_fill_profile,
        route = ScreenDestinations.Profile.createRoute(-1),
    )
}
