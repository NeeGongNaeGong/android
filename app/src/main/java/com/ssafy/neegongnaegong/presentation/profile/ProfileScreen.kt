package com.ssafy.neegongnaegong.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.profile.compopnent.ProfileContent
import com.ssafy.neegongnaegong.presentation.profile.compopnent.WithdrawalDialog

@Composable
fun ProfileScreen(
    profileImg: String,
    nickname: String,
    onClickNotification: () -> Unit,
    onClickNotice: () -> Unit,
    onClickPrivacyInfo: () -> Unit,
    onClickDeleteAccount: () -> Unit
) {
    val (showDialog, setShowDialog) = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Text(text = profileImg)
        Text(text = nickname)

        ProfileContent(title = "알람", onClick = onClickNotification)

        ProfileContent(title = "공지사항", onClick = onClickNotice)

        ProfileContent(title = "개인 정보 처리 방침 ", onClick = onClickPrivacyInfo)

        ProfileContent(title = "회원 탈퇴", onClick = { setShowDialog(true) })

        if (showDialog) {
            WithdrawalDialog(
                onDismiss = { setShowDialog(false) },
                onConfirm = onClickDeleteAccount
            )
        }
    }
}
