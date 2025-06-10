package com.ssafy.neegongnaegong.presentation.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.profile.compopnent.ProfileContent
import com.ssafy.neegongnaegong.presentation.profile.compopnent.ProfileImage
import com.ssafy.neegongnaegong.presentation.profile.compopnent.ProfileNickname
import com.ssafy.neegongnaegong.presentation.profile.compopnent.WithdrawalDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews

@Composable
fun ProfileScreen(
    profileImg: String,
    nickname: String,
    isEditing: Boolean,
    hasUnReadNotification: Boolean,
    onChangeNickName: (String) -> Unit,
    onClickEdit: () -> Unit,
    onClickEditCancel: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onClickNotification: () -> Unit,
    onClickNotice: () -> Unit,
    onClickPrivacyInfo: () -> Unit,
    onClickLogout: () -> Unit,
    onClickDeleteAccount: () -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileImage(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            profileImg = profileImg,
            onImageSelected = onImageSelected
        )

        ProfileNickname(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            isEditing = isEditing,
            text = nickname,
            onClickEdit = onClickEdit,
            onClickEditCancel = onClickEditCancel,
            onClickEditDone = onChangeNickName
        )

        Spacer(modifier = Modifier.height(18.dp))

        ProfileContent(
            title = stringResource(id = R.string.notification),
            onClick = onClickNotification,
            icon = {
                if (hasUnReadNotification) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(8.dp)
                            .background(Color.Red, shape = CircleShape)
                    )
                }
            }
        )

        ProfileContent(
            title = stringResource(id = R.string.notice),
            onClick = onClickNotice
        )

        ProfileContent(
            title = stringResource(id = R.string.privacy_policy),
            onClick = onClickPrivacyInfo
        )

        ProfileContent(
            title = stringResource(id = R.string.logout),
            onClick = onClickLogout
        )

        ProfileContent(
            title = stringResource(id = R.string.account_delete),
            onClick = { showDialog = true }
        )

        if (showDialog) {
            WithdrawalDialog(
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    onClickDeleteAccount()
                }
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun ProfileScreenPreview() {
    ProfileScreen(
        profileImg = "",
        nickname = "닉네임",
        isEditing = false,
        hasUnReadNotification = false,
        onChangeNickName = {},
        onClickEdit = {},
        onClickEditCancel = {},
        onImageSelected = {},
        onClickNotification = {},
        onClickNotice = {},
        onClickPrivacyInfo = {},
        onClickLogout = {},
        onClickDeleteAccount = {},
    )
}
