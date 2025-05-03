package com.ssafy.neegongnaegong.domain.model.studies

import androidx.compose.ui.graphics.Color
import com.ssafy.neegongnaegong.presentation.group.component.detail.MedalType

data class ProfileData(
    val id: Long,
    val medalType: MedalType = MedalType.NONE,
    val imageUrl: String,
    val name: String,
    val progress: Float,
    val ringColors: List<Color> = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
)