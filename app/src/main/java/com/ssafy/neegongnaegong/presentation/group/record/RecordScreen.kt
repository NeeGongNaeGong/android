package com.ssafy.neegongnaegong.presentation.group.record

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordList
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme


@Composable
fun RecordRoute(
    groupId: Int,
    memberId: Int,
    popBackStack: () -> Boolean,
    modifier: Modifier = Modifier,
//    viewModel: RecordViewModel = hiltViewModel()
) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "박준식 이번주 공부내용",
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                )
            },
            onNavigationClick = { popBackStack() }
        )

        RecordContent(
            modifier
                .weight(1F)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordTopBar(
    popBackStack: () -> Boolean,
) {
    // title을 가운데로 위치시켜주는 CenterAlignedTopAppBar
    // https://stackoverflow.com/questions/67497414/how-to-align-title-at-layout-center-in-topappbar
    CenterAlignedTopAppBar(
        title = {
            Text(
                style = NeeGongNaeGongTheme.typography.titleSmall,
                text = "박준식 이번주 공부내용"
            )
        },
        navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기 버튼"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = NeeGongNaeGongTheme.colorScheme.background,
            titleContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
            navigationIconContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
            actionIconContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    )
}

@Composable
fun RecordContent(
    modifier: Modifier = Modifier,
) {
    var chartHeight by remember { mutableStateOf(0.dp) }

    Column(modifier = modifier) {

        Row(
            modifier = Modifier.padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            PieChartScreen(
                modifier = Modifier
                    .height(if (chartHeight > 0.dp) chartHeight else Dp.Unspecified)
                    .aspectRatio(1F)
            )
            Spacer(modifier = Modifier.width(10.dp))
            ChartLegendScreen(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(if (chartHeight > 0.dp) chartHeight else Dp.Unspecified)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                onHeightChange = { newHeight -> chartHeight = newHeight },
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        StudyRecordList(
            modifier = Modifier.weight(1F),
            studyRecords = PersonalPreviewDataProvider().getStudyRecords(),
            onClick = {}
        )

    }

}

@Composable
fun PieChartScreen(
    modifier: Modifier = Modifier,
) {
// 1) 애니메이션 시작 플래그
    var startAnimation by remember { mutableStateOf(false) }

    val slices: List<Float> =
        listOf(0.3F, 0.5F, 0.6F, 0.1F, 0.2F)           // 각 조각의 값 (합은 100이 아니어도 됨)
    val colors: List<Color> =
        listOf(
            NeeGongNaeGongTheme.colorScheme.peach,
            NeeGongNaeGongTheme.colorScheme.lightGreen,
            NeeGongNaeGongTheme.colorScheme.blue,
            NeeGongNaeGongTheme.colorScheme.mintBlue,
            Color.Magenta
        )

    val animationDuration: Int = 1000
    // 전체 합계 계산
    val total = remember(slices) { slices.sum() }


    // 0f → 1f 으로 자동 애니메이트
    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0F,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = LinearEasing
        )
    )



    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Canvas(
        modifier = modifier,
    ) {
        var startAngle = -90f

        slices.forEachIndexed { index, sliceValue ->
            // 이 조각이 차지할 각도
            val sweepAngle = 360f * (sliceValue / total)

            // 현재 애니메이션 진행도(progress.value * 360)와 비교해
            // 이 조각이 완전히 그려질지, 일부만 그려질지 결정
            // maxDrawAngle은 Animation으로 밀리초동안 0~1값으로 변함
            // => 예를 들어 애니메이션이 1초일 때 0.5초 지났으면 0.5가 되어 있을 거고 그럼 현재는 절반까지만 그려져 있어야 함
            val maxDrawAngle = progress * 360f - 90

            // 그릴 양 정하기
            val drawSweep = when {
                maxDrawAngle >= startAngle + sweepAngle -> sweepAngle
                maxDrawAngle <= startAngle -> 0f
                else -> maxDrawAngle - startAngle
            }

            // 조각 그리기
            drawArc(
                color = colors.getOrElse(index) { Color.Black },
                startAngle = startAngle,
                sweepAngle = drawSweep,
                useCenter = true
            )

            startAngle += drawSweep
        }
    }
}

@Composable
fun ChartLegendScreen(onHeightChange: (Dp) -> Unit, modifier: Modifier = Modifier) {

    val legendItems: List<Triple<String, String, Color>> =
        listOf(
            Triple("알고리즘", "5H", NeeGongNaeGongTheme.colorScheme.peach),
            Triple("네트워크", "2H 45M", NeeGongNaeGongTheme.colorScheme.lightGreen),
            Triple("운영체제", "4H", NeeGongNaeGongTheme.colorScheme.blue),
            Triple("영어회화", "3H 30M", NeeGongNaeGongTheme.colorScheme.mintBlue),
            Triple("데이터 베이스", "1H", Color.Magenta),
        )

    val density = LocalDensity.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        legendItems.forEachIndexed { index, item ->
            Column(
                modifier = if (index == 0) {
                    Modifier
                        .wrapContentSize()//.background(Color.Blue)
                        .onSizeChanged { size -> // 2. 크기 변경 시 높이 측정
                            if (size.height > 0) { // 초기 0 값 무시
                                onHeightChange(with(density) {
                                    size.height.toDp() * 4 + if (legendItems.size > 1) {
                                        (-15).dp
                                    } else {
                                        0.dp
                                    }
                                })
                            }
                        }
                } else {
                    Modifier.wrapContentSize()
                }

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(item.third)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            modifier = Modifier.basicMarquee(
                                iterations = Int.MAX_VALUE,

                                ),
                            style = NeeGongNaeGongTheme.typography.labelMedium,
                            color = NeeGongNaeGongTheme.colorScheme.chartLegend,
                            text = item.first, maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(Modifier.height(5.dp))
                Text(
                    modifier = Modifier.offset(x = (20).dp),
                    text = item.second,
                    maxLines = 1,
                    style = NeeGongNaeGongTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    color = NeeGongNaeGongTheme.colorScheme.blue,
                )
                if (index != legendItems.lastIndex) {
                    Spacer(Modifier.height(15.dp))
                }
            }

        }

    }
}


@NeeGongNaeGongPreviews
@Composable
fun PreviewTopAppBar() {
    NeeGongNaeGongTheme {
        RecordTopBar(popBackStack = { true })
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewRecordContent() {
    NeeGongNaeGongTheme {
        RecordContent(
            Modifier
                .fillMaxSize(),
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewPieChart() {
    NeeGongNaeGongTheme {
        PieChartScreen(
            Modifier
                .fillMaxSize(),
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewChartLegend() {
    NeeGongNaeGongTheme {
        ChartLegendScreen({})
    }
}
