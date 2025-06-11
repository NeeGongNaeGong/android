import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun VotedPersonListRoute(
    title: String,
    votedMemberInfoList: List<StudyGroupVoteStatusInfo.VotedMemberInfo>,
    popBackStack: () -> Boolean,
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    modifier =
                        Modifier.basicMarquee(
                            iterations = Int.MAX_VALUE,
                            animationMode = MarqueeAnimationMode.Immediately,
                            initialDelayMillis = 1000,
                            repeatDelayMillis = 1000,
                            velocity = 50.dp,
                        ),
                    text = "$title 항목 투표자 상세보기",
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            onNavigationClick = { popBackStack() },
        )
        LazyColumn(modifier = Modifier.padding(horizontal = NeeGongNaeGongTheme.paddingScheme.sp2)) {
            items(
                key = { votedMemberInfo -> votedMemberInfo.id },
                items = votedMemberInfoList,
            ) { votedMemberInfo ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    GlideImage(
                        imageModel = { votedMemberInfo.profileImg },
                        loading = { CircularProgressIndicator() },
                        modifier =
                            Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        imageOptions =
                            ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                            ),
                        requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) },
                        failure = {
                            // 이미지 로드 실패 시 플레이스홀더
                            Image(
                                painter = painterResource(id = R.drawable.img_default_profile),
                                contentDescription = "Profile Image",
                                modifier =
                                    Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop,
                            )
                        },
                    )

                    Text(
                        text = votedMemberInfo.username,
                        style = NeeGongNaeGongTheme.typography.titleSmall,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewVotePersonListRoute() {
    val list =
        mutableListOf<StudyGroupVoteStatusInfo.VotedMemberInfo>().apply {
            repeat(3) {
                add(
                    StudyGroupVoteStatusInfo.VotedMemberInfo(
                        id = 24,
                        username = "잠자는 사자 12",
                        profileImg = "https://neegongnaegong-dev.s3.ap-northeast-2.amazonaws.com/defaultImage/defaultUserImage.jpg",
                    ),
                )
            }
        }
    NeeGongNaeGongTheme {
        VotedPersonListRoute(
            title = "",
            votedMemberInfoList = list,
        ) { true }
    }
}
