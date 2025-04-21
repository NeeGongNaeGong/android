package com.ssafy.neegongnaegong.presentation.group.component.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.studies.ProfileData
import com.ssafy.neegongnaegong.presentation.group.component.detail.ProfileCircleRing
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ProfilesSection(
    modifier: Modifier = Modifier,
    profiles: List<ProfileData>,
    onProfileClick: (Long) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(profiles) { profile ->
                    ProfileCircleRing(
                        imageUrl = profile.imageUrl,
                        name = profile.name,
                        progress = profile.progress,
                        ringColors = profile.ringColors,
                        animationDuration = 600, // 빠른 애니메이션
                        medalType = profile.medalType,
                        onClick = { onProfileClick(profile.id) },
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewProfilesSection() {
    val sampleProfiles =
        listOf(
            ProfileData(
                id = 1,
                imageUrl = "https://example.com/image1.jpg",
                name = "11",
                progress = 0.75f,
                ringColors =
                    listOf(
                        Color.Blue,
                        Color.Cyan,
                    ),
            ),
            ProfileData(
                id = 2,
                imageUrl = "https://example.com/image2.jpg",
                name = "22",
                progress = 0.5f,
                ringColors =
                    listOf(
                        Color.Red,
                        Color.Yellow,
                    ),
            ),
            ProfileData(
                id = 3,
                imageUrl = "https://example.com/image3.jpg",
                name = "33",
                progress = 0.25f,
                ringColors =
                    listOf(
                        Color.Green,
                        Color.Yellow,
                    ),
            ),
        )
    NeeGongNaeGongTheme {
        ProfilesSection(
            profiles = sampleProfiles,
            onProfileClick = {},
        )
    }
}
