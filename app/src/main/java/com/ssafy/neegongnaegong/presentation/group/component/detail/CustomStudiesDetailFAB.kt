package com.ssafy.neegongnaegong.presentation.group.component.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import com.ssafy.neegongnaegong.presentation.util.noRippleClickable
import kotlinx.coroutines.delay

/**
 * FloatingActionButton
 * @param modifier
 */
@Composable
fun CustomStudiesFAB(
    modifier: Modifier = Modifier,
    navigateToAuctionRegister: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.End,
            modifier = modifier.padding(16.dp),
        ) {
            CustomStudiesSubFAB(
                visible = expanded,
                delayMillis = 200,
                textLabel = stringResource(id = R.string.studies_detail_fab_calender),
                imgRes = R.drawable.ic_studies_fab_calender,
            )
            CustomStudiesSubFAB(
                visible = expanded,
                delayMillis = 100,
                textLabel = stringResource(id = R.string.studies_detail_fab_voting),
                imgRes = R.drawable.ic_studies_fab_voting,
            )
            CustomStudiesSubFAB(
                visible = expanded,
                delayMillis = 0,
                textLabel = stringResource(id = R.string.studies_detail_fab_announcement),
                imgRes = R.drawable.ic_studies_fab_announcement,
                onClick = navigateToAuctionRegister,
            )

            // 메인 FAB 버튼 - 테마 컬러 적용
            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = MaterialTheme.colorScheme.primary, // 테마 적용
                contentColor = MaterialTheme.colorScheme.onPrimary, // 테마 적용
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_studies_fab_plus),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun CustomStudiesSubFAB(
    modifier: Modifier = Modifier,
    visible: Boolean,
    delayMillis: Int,
    textLabel: String,
    imgRes: Int,
    onClick: () -> Unit = {},
) {
    var isVisible by remember { mutableStateOf(false) }

    // 애니메이션 지연
    LaunchedEffect(visible) {
        if (visible) {
            delay(delayMillis.toLong())
            isVisible = true
        } else {
            isVisible = false
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
    ) {
        Row(
            modifier =
                modifier
                    .padding(end = 10.dp)
                    .noRippleClickable {
                        onClick()
                    },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = textLabel,
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.labelMedium,
                modifier =
                    modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp),
                        ).padding(horizontal = 8.dp, vertical = 4.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier =
                    modifier
                        .size(32.dp)
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = imgRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomStudiesDetailFAB() {
    NeeGongNaeGongTheme {
        CustomStudiesFAB()
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun PreviewDarkCustomStudiesDetailFAB() {
    NeeGongNaeGongTheme(darkTheme = true) {
        CustomStudiesFAB()
    }
}
