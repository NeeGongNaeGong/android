package com.ssafy.neegongnaegong.presentation.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Phone, Foldable, Tablet에 대한 세로/가로, 라이트/다크 모드를
 * 모두 포함하는 최종 미리보기 어노테이션입니다. (총 12개)
 */
@Preview(
    name = "01. Phone - Portrait Light",
    group = "Phone",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF,
    device = PHONE_PORTRAIT,
)
@Preview(
    name = "02. Phone - Portrait Dark",
    group = "Phone",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
    device = PHONE_PORTRAIT,
)
@Preview(
    name = "03. Phone - Landscape Light",
    group = "Phone",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF,
    device = PHONE_LANDSCAPE,
)
@Preview(
    name = "04. Phone - Landscape Dark",
    group = "Phone",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
    device = PHONE_LANDSCAPE,
)
@Preview(
    name = "05. Foldable - Portrait Light",
    group = "Foldable",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF,
    device = FOLDABLE_PORTRAIT,
)
@Preview(
    name = "06. Foldable - Portrait Dark",
    group = "Foldable",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
    device = FOLDABLE_PORTRAIT,
)
@Preview(
    name = "07. Foldable - Landscape Light",
    group = "Foldable",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF,
    device = FOLDABLE_LANDSCAPE,
)
@Preview(
    name = "08. Foldable - Landscape Dark",
    group = "Foldable",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
    device = FOLDABLE_LANDSCAPE,
)
@Preview(
    name = "09. Tablet - Portrait Light",
    group = "Tablet",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF,
    device = TABLET_PORTRAIT,
)
@Preview(
    name = "10. Tablet - Portrait Dark",
    group = "Tablet",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
    device = TABLET_PORTRAIT,
)
@Preview(
    name = "11. Tablet - Landscape Light",
    group = "Tablet",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF,
    device = TABLET_LANDSCAPE,
)
@Preview(
    name = "12. Tablet - Landscape Dark",
    group = "Tablet",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
    device = TABLET_LANDSCAPE,
)
annotation class NeeGongNaeGongPreviews

private const val PHONE_PORTRAIT = "spec:id=reference_phone,shape=Normal,width=411dp,height=891dp,,dpi=420"
private const val PHONE_LANDSCAPE = "spec:id=reference_phone,shape=Normal,width=891dp,height=411dp,dpi=420,orientation=landscape"
private const val FOLDABLE_PORTRAIT =
    "spec:id=reference_foldable,shape=Normal,width=673dp,height=841dp,dpi=420"
private const val FOLDABLE_LANDSCAPE =
    "spec:id=reference_foldable,shape=Normal,width=841dp,height=673dp,dpi=420,orientation=landscape"
private const val TABLET_PORTRAIT = "spec:width=853dp,height=1365dp,dpi=276"
private const val TABLET_LANDSCAPE = "spec:width=1365dp,height=853dp,dpi=276,orientation=landscape"
