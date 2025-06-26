package com.ssafy.neegongnaegong.presentation.group.component.detail

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.delay

@Composable
fun ProfileCircleRing(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    progress: Float, // 0.0f ~ 1.0f 사이의 값 (0% ~ 100%)
    ringColors: List<Color> = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
    size: Int = 80,
    imageSizeRatio: Float = 0.75f, // 이미지 크기의 비율 (전체 크기 대비)
    ringWidth: Int = 4,
    animationDuration: Int = 800, // 애니메이션 시간 (ms)
    medalType: MedalType = MedalType.NONE, // 메달 타입
    onClick: () -> Unit = {},
) {
    val sizeDp = size.dp
    val imageSizeDp = (size * imageSizeRatio).dp // 이미지 크기를 전체 크기의 비율로 계산
    val isPreviewMode = LocalInspectionMode.current

    var animate by rememberSaveable { mutableStateOf(false) }

    // 목표 진행률에 도달하기 위한 애니메이션 적용
    val animatedProgress by animateFloatAsState(
        targetValue = if (animate) progress else 0f,
        animationSpec =
            tween(
                durationMillis = animationDuration,
                easing = LinearEasing,
            ),
        label = "ProgressAnimation",
    )

    // 컴포넌트가 처음 생성될 때만 한 번 실행
    LaunchedEffect(Unit) {
        // 컴포넌트가 나타나면 약간 지연 후 애니메이션 시작
        delay(100) // 100ms 지연 (필요시 조정)
        animate = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .size(sizeDp)
                    .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            // 링 그리기
            Canvas(
                modifier = Modifier.fillMaxSize(),
            ) {
                // 배경 회색 링
                drawArc(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = ringWidth.dp.toPx(), cap = StrokeCap.Round),
                )

                // 진행도에 따른 컬러 링 (12시 방향부터 시계방향으로)
                val brush = Brush.linearGradient(colors = ringColors)

                // 진행률에 따른 링
                drawArc(
                    brush = brush,
                    startAngle = 270f, // 12시 방향부터 시작
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = ringWidth.dp.toPx(), cap = StrokeCap.Round),
                )
            }

            // 프로필 이미지
            Box(
                modifier =
                    Modifier
                        .size(imageSizeDp)
                        .clip(CircleShape)
                        .clickable { onClick() },
            ) {
                if (isPreviewMode) {
                    // 프리뷰 모드에서는 로컬 리소스 사용
                    Image(
                        painter = painterResource(id = R.drawable.img_default_profile),
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    // 실제 앱 실행 시에는 Glide 사용
                    GlideImage(
                        imageModel = { imageUrl },
                        modifier = Modifier.fillMaxSize(),
                        failure = {
                            // 이미지 로드 실패 시 플레이스홀더
                            Image(
                                painter = painterResource(id = R.drawable.img_default_profile),
                                contentDescription = "Profile Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        },
                    )
                }
            }

            // 메달 아이콘 (조건부 표시)
            if (medalType != MedalType.NONE) {
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 2.dp, y = 2.dp)
                            .size(size.dp / 3)
                            .padding(2.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter =
                            painterResource(
                                id =
                                    when (medalType) {
                                        MedalType.GOLD -> R.drawable.ic_studies_medal_gold
                                        MedalType.SILVER -> R.drawable.ic_studies_medal_silver
                                        else -> R.drawable.ic_studies_medal_bronze
                                    },
                            ),
                        contentDescription = "Medal",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified,
                    )
                }
            }
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = name,
            style = NeeGongNaeGongTheme.typography.labelSmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewProfileCircleRing() {
    NeeGongNaeGongTheme {
        ProfileCircleRing(
            imageUrl = "https://example.com/image.jpg",
            name = "크롱",
            progress = 0.75f,
            ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
            medalType = MedalType.GOLD,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewMultipleProfileCircleRing() {
    NeeGongNaeGongTheme {
        Row {
            ProfileCircleRing(
                imageUrl = "https://example.com/image1.jpg",
                name = "포비",
                progress = 0.25f,
                ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
                medalType = MedalType.GOLD,
            )
            ProfileCircleRing(
                imageUrl = "https://example.com/image2.jpg",
                name = "루피",
                progress = 0.5f,
                ringColors = listOf(Color(0xFF4CAF50), Color(0xFF8BC34A)),
                medalType = MedalType.SILVER,
            )
            ProfileCircleRing(
                imageUrl = "https://example.com/image3.jpg",
                name = "에디",
                progress = 0.75f,
                ringColors = listOf(Color(0xFF2196F3), Color(0xFF03A9F4)),
                medalType = MedalType.BRONZE,
            )
            ProfileCircleRing(
                imageUrl = "https://example.com/image4.jpg",
                name = "뽀로로",
                progress = 1.0f,
                ringColors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63)),
                medalType = MedalType.NONE,
            )
        }
    }
}

enum class MedalType {
    NONE,
    GOLD,
    SILVER,
    BRONZE,
}

enum class RingColor(
    val colors: List<Color>,
) {
    SPRING(listOf(Color(0xFF4CAF50), Color(0xFF8BC34A))),
    SUMMER(listOf(Color(0xFF9C27B0), Color(0xFFE91E63))),
    AUTUMN(listOf(Color(0xFFFF9800), Color(0xFFFF5722))),
    WINTER(listOf(Color(0xFF2196F3), Color(0xFF03A9F4))),
}
