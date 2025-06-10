package com.ssafy.neegongnaegong.presentation.profile.compopnent

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    shouldShowProfileImageWarningInfo: Boolean,
    profileImg: String,
    onCheckProfileImageWarning: () -> Unit,
    onImageSelected: (Uri) -> Unit,
) {
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    val contract: ActivityResultContract<String, Uri?> = ActivityResultContracts.GetContent()
    val launcher = rememberLauncherForActivityResult(contract = contract) { uri: Uri? ->
        uri?.let { onImageSelected(uri) }
    }

    Box {
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

        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = "Edit Profile Image",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(30.dp)
                .clip(CircleShape)
                .shadow(14.dp, CircleShape)
                .background(Color(0xFFF5F5F5), CircleShape)
                .padding(8.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        if (shouldShowProfileImageWarningInfo) {
                            showDialog = true
                        } else {
                            launcher.launch("image/*")
                        }
                    }
                )
        )
    }

    if (showDialog) {
        ProfileImageWarningDialog(
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                showDialog = false
                onCheckProfileImageWarning()
                launcher.launch("image/*")
            }
        )
    }
}
