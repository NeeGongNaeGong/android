package com.ssafy.neegongnaegong.presentation.profile.compopnent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    profileImg: String
) {
    GlideImage(
        imageModel = { profileImg },
        modifier = modifier,
        failure = {
            GlideImage(
                imageModel = { R.drawable.img_main_character },
                modifier = modifier,
            )
        }
    )
}
