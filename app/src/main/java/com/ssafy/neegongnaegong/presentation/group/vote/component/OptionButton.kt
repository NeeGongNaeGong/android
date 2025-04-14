package com.ssafy.neegongnaegong.presentation.group.vote.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OptionButton( isSelected: Boolean, optionTitle: String, modifier : Modifier = Modifier, onClick: () -> Unit,) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if(isSelected)Icons.Filled.Circle else Icons.Outlined.Circle,
                contentDescription = "선택 여부"
            )
        }
        Text(optionTitle)
    }

}

@Preview
@Composable
fun PreviewOptionButton(){
    OptionButton(false, "종료 시간"){}
}