package com.ssafy.neegongnaegong.presentation.group.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun StudiesImagePicker(
    modifier: Modifier = Modifier,
    selectedImageUri: Uri?,
    profileImage: String?,
    onClick: () -> Unit,
    clearSelectedImage: () -> Unit,
    clearProfileImage: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(120.dp)
                .clip(
                    RoundedCornerShape(12.dp),
                )
                .clickable {
                    onClick()
                },
    ) {
        if (selectedImageUri != null || profileImage != null) {
            GlideImage(
                imageModel = { selectedImageUri ?: profileImage },
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                },
                failure = {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.errorContainer),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "이미지 로드 실패",
                            modifier = Modifier.align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                },
            )
            Box(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(24.dp)
                        .background(
                            color =
                                NeeGongNaeGongTheme.colorScheme.background.copy(
                                    alpha = 0.7f,
                                ),
                            shape = CircleShape,
                        )
                        .clickable {
                            clearSelectedImage()
                            clearProfileImage()
                        },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "이미지 삭제",
                    tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                    modifier = Modifier.size(16.dp),
                )
            }
        } else {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = NeeGongNaeGongTheme.colorScheme.blue),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.studies_edit_add_photo),
                    tint = NeeGongNaeGongTheme.colorScheme.background,
                )
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewStudiesImagePicker() {
    NeeGongNaeGongTheme {
        StudiesImagePicker(
            modifier = Modifier,
            selectedImageUri = null,
            profileImage = null,
            onClick = {},
            clearSelectedImage = {},
            clearProfileImage = {},
        )
    }
}
