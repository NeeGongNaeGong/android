package com.ssafy.neegongnaegong.presentation.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

// Play Store로 이동 (fallback)
fun openPlayStoreForUpdate(context: Context) {
    val packageName = context.packageName
    val intent =
        Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri()).apply {
            setPackage("com.android.vending")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    context.startActivity(intent)
}
