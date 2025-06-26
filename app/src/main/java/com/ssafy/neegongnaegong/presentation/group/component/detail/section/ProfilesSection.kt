package com.ssafy.neegongnaegong.presentation.group.component.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.studies.WeeklyRankingsMember
import com.ssafy.neegongnaegong.presentation.group.component.detail.MedalType
import com.ssafy.neegongnaegong.presentation.group.component.detail.ProfileCircleRing
import com.ssafy.neegongnaegong.presentation.group.component.detail.RingColor
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.TimeUnit

@Composable
fun ProfilesSection(
    modifier: Modifier = Modifier,
    weeklyRankings: List<WeeklyRankingsMember>,
    studyGoalTime: Int,
    onLoadMore: () -> Unit,
    onProfileClick: (Long) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(weeklyRankings) { index, member ->
                val currentProgress =
                    member.studyTimeSeconds.toFloat() / studyGoalTime
                val medalType: MedalType =
                    when (index) {
                        0 -> MedalType.GOLD
                        1 -> MedalType.SILVER
                        2 -> MedalType.BRONZE
                        else -> MedalType.NONE
                    }
                val ringColors: RingColor =
                    when (index % 4) {
                        0 -> RingColor.SPRING
                        1 -> RingColor.SUMMER
                        2 -> RingColor.AUTUMN
                        else -> RingColor.WINTER
                    }
                ProfileCircleRing(
                    imageUrl = member.profileImageUrl,
                    name = member.name,
                    progress = currentProgress,
                    ringColors = ringColors.colors,
                    medalType = medalType,
                    onClick = { onProfileClick(member.userId) },
                )
                if (index == weeklyRankings.lastIndex) {
                    LaunchedEffect(Unit) {
                        onLoadMore()
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewProfilesSection() {
    val weeklyRankingsMember =
        listOf(
            WeeklyRankingsMember(
                userId = 1,
                name = "11",
                profileImageUrl = "https://example.com/image1.jpg",
                studyTimeSeconds = TimeUnit.HOUR.seconds * 10,
            ),
            WeeklyRankingsMember(
                userId = 1,
                name = "22",
                profileImageUrl = "https://example.com/image1.jpg",
                studyTimeSeconds = TimeUnit.HOUR.seconds * 9,
            ),
            WeeklyRankingsMember(
                userId = 1,
                name = "33",
                profileImageUrl = "https://example.com/image1.jpg",
                studyTimeSeconds = TimeUnit.HOUR.seconds * 8,
            ),
            WeeklyRankingsMember(
                userId = 1,
                name = "44",
                profileImageUrl = "https://example.com/image1.jpg",
                studyTimeSeconds = TimeUnit.HOUR.seconds * 7,
            ),
        )
    NeeGongNaeGongTheme {
        ProfilesSection(
            weeklyRankings = weeklyRankingsMember,
            studyGoalTime = (TimeUnit.HOUR.seconds * 12).toInt(),
            onLoadMore = {},
            onProfileClick = {},
        )
    }
}
