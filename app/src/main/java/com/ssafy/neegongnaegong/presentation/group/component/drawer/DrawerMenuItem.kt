package com.ssafy.neegongnaegong.presentation.group.component.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography


@Composable
fun DrawerMenuItem(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    iconTint: Color = Color.Unspecified,
    title: String,
    subtitle: String? = null,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp),
                ).clickable { onClick() }
                .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        Column {
            Text(
                text = title,
                style = NeeGongNaeGongTheme.typography.bodyLarge,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDrawerMenuItem() {
    NeeGongNaeGongTheme {
        DrawerMenuItem(
            icon = android.R.drawable.ic_menu_info_details,
            iconTint = Color.Blue,
            title = "제목",
            subtitle = "부 제목",
            backgroundColor = Color.LightGray.copy(alpha = 0.3f),
            onClick = {},
        )
    }
}
